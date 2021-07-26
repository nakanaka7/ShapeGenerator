package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.SgRootCommandHelpFactory;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.BranchCommandHelpNew;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.commadHelp.RootCommandHelp;
import tokyo.nakanaka.commadHelp.RootCommandHelpNew;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.LogDesignColor;
import tokyo.nakanaka.logger.LogTemplate;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class SgHelpCommandHandler implements CommandHandler {
	private SgCommandDirectory sgCmdDir;
	private BranchCommandHelp cmdHelp;
	private SgRootCommandHelpFactory helpFactory;
	
	public SgHelpCommandHandler(SgCommandDirectory sgCmdDir) {
		this.sgCmdDir = sgCmdDir;
		this.cmdHelp = new BranchCommandHelp.Builder("help")
				.description("Print command help")
				.addParameter(ParameterType.OPTIONAL, "subcommand")
				.build();
		this.helpFactory = new SgRootCommandHelpFactory(sgCmdDir);
	}
	
	@Override
	public String getLabel() {
		return "help";
	}
	
	@Override
	public String getDescription() {
		return "Print command help";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.OPTIONAL, "subcommand", ""));
		return list;
	}
	
	@Override
	public BranchCommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}
		
	private void onCommandOld(CommandHelp help, Player player, String[] args) {
		Logger logger = player.getLogger();
		String label = help.getLabel();
		CommandHelp subHelp = help.getSubHelp(args);
		if(subHelp == null) {
			logger.print(LogDesignColor.ERROR + "Usage: " + "/" + label + " " + this.cmdHelp.getUsage());
			return;
		}
		
		String title = "Help for /" + label;
		if(args.length != 0) {
			title += " " + String.join(" ", args);
		}
		logger.print(LogTemplate.ofLine(title));
		logger.print(LogColor.GOLD + "Description: " + LogColor.RESET + subHelp.getDescription());
		String usage = "/" + label + " " + String.join(" ", args);
		if(subHelp instanceof RootCommandHelp) {
			usage += " " + "<subcommand>"; 
		}else if(subHelp instanceof BranchCommandHelp) {
			usage += " " + ((BranchCommandHelp) subHelp).getParameterUsage(); 
		}
		logger.print(LogColor.GOLD + "Usage: " + LogColor.RESET + usage);
		if(subHelp instanceof RootCommandHelp) {
			logger.print(LogColor.GOLD + "SubCommand:");
			List<CommandHelp> subList = ((RootCommandHelp) subHelp).getSubCommandHelp();
			for(CommandHelp e : subList) {
				logger.print("  " + LogColor.GOLD +e.getLabel() + ": " + LogColor.RESET + e.getDescription());
			}
			logger.print(LogDesignColor.NORMAL + "Type " 
			+ "\"/" + label + " " + "help " + String.join(" ", args) + " <subcommand>\"" 
			+ " for the subcommand detail");
		}
	}
	
	
	public void onCommandOld(Player player, String[] args) {
		RootCommandHelp help = this.helpFactory.create(player);
		this.onCommandOld(help, player, args);
	}
	
	public void onCommand(Player player, String[] args) {
		onRecursiveCommand(new String[0], this.sgCmdDir, player, args);
	}
	
	private static boolean onRecursiveCommand(String[] parentLabels, CommandEntry cmdEntry, Player player, String[] args) {
		Logger logger = player.getLogger();
		if(cmdEntry instanceof CommandHandler) {
			if(args.length != 0) {
				return false;
			}
			CommandHandler cmdHandler = (CommandHandler)cmdEntry;
			BranchCommandHelpNew cmdHelp = new BranchCommandHelpNew(parentLabels, cmdHandler);
			logger.print(LogTemplate.ofLine("Help for " + cmdHelp.getSubject()));
			logger.print(LogTemplate.ofKeyValue("Description", cmdHelp.getDescription()));
			logger.print(LogTemplate.ofKeyValue("Usage", cmdHelp.getUsage()));
			logger.print(LogTemplate.ofKeyValue("Arguments", ""));
			cmdHelp.getParameterDescriptionList().stream()
				.forEach(s -> logger.print("  " + LogTemplate.ofKeyValue(s.getFirst(), s.getSecond())));
			return true;
		}else if(cmdEntry instanceof CommandDirectory) {
			CommandDirectory cmdDir = (CommandDirectory)cmdEntry;
			if(args.length == 0) {
				RootCommandHelpNew cmdHelp = new RootCommandHelpNew(parentLabels, cmdDir);
				logger.print(LogTemplate.ofLine("Help for " + cmdHelp.getSubject()));
				logger.print(LogTemplate.ofKeyValue("Description", cmdHelp.getDescription()));
				logger.print(LogTemplate.ofKeyValue("Usage", cmdHelp.getUsage()));
				logger.print(LogTemplate.ofKeyValue("SubCommand", ""));
				cmdHelp.getSubCommandDescriptionList(player).stream()
				.forEach(s -> logger.print("  " + LogTemplate.ofKeyValue(s.getFirst(), s.getSecond())));
				return true;
			}
			List<CommandEntry> subList = cmdDir.getSubList(player);
			String subLabel = args[0];
			CommandEntry subEntry = null;
			for(CommandEntry e : subList) {
				if(e.getLabel().equals(subLabel)) {
					subEntry = e;
					break;
				}
			}
			if(subEntry == null) {
				logger.print(LogDesignColor.ERROR + "Unknown subcommand");
				return true;
			}
			String[] subParentLabels = new String[parentLabels.length + 1];
			System.arraycopy(parentLabels, 0, subParentLabels, 0, parentLabels.length);
			subParentLabels[parentLabels.length] = cmdEntry.getLabel();
			String[] subArgs = new String[args.length - 1];
			System.arraycopy(args, 1, subArgs, 0, args.length - 1);
			return onRecursiveCommand(subParentLabels, subEntry, player, subArgs);
		}else {
			//unreachable
			return false;
		}
	}
	
	public List<String> onTabCompleteNew(Player player, String[] args) {
		return onRecursiveTabCompleteNew(this.sgCmdDir, player, args);
	}
	
	private static List<String> onRecursiveTabCompleteNew(CommandEntry cmdEntry, Player player, String[] args) {
		if(cmdEntry instanceof CommandHandler) {
			return new ArrayList<>();
		}else if(cmdEntry instanceof CommandDirectory) {
			CommandDirectory cmdDir = (CommandDirectory)cmdEntry;
			List<CommandEntry> subList = cmdDir.getSubList(player);
			if(args.length == 1) {
				return subList.stream()
						.map(s -> s.getLabel())
						.collect(Collectors.toList());
			}else {
				String subLabel = args[0];
				CommandEntry subEntry = null;
				for(CommandEntry e : subList) {
					if(e.getLabel().equals(subLabel)) {
						subEntry = e;
						break;
					}
				}
				if(subEntry == null) {
					return new ArrayList<>();
				}
				String[] subArgs = new String[args.length - 1];
				System.arraycopy(args, 1, subArgs, 0, args.length - 1);
				return onRecursiveTabCompleteNew(subEntry, player, subArgs);
			}
		}else {
			return new ArrayList<>();
		}
	}
	
	public List<String> onTabComplete(Player player, String[] args) {
		RootCommandHelp sgHelp = this.helpFactory.create(player);
		return onRecursiveTabComplete(sgHelp, player, args);
	}
	
	/**
	 * A recursive process of onTabComplete method
	 */
	private List<String> onRecursiveTabComplete(CommandHelp help, Player player, String[] args) {
		String subLabel = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		if(args.length == 1) {
			if(help instanceof RootCommandHelp) {
				List<CommandHelp> subHelpList = ((RootCommandHelp) help).getSubCommandHelp();
				return subHelpList.stream()
						.map(s -> s.getLabel())
						.collect(Collectors.toList());
			}else {
				return new ArrayList<>();
			}	
		}
		if(help instanceof RootCommandHelp) {
			CommandHelp subHelp = help.getSubHelp(subLabel);
			return onRecursiveTabComplete(subHelp, player, shiftArgs);
		}
		return new ArrayList<>();
	}

}
