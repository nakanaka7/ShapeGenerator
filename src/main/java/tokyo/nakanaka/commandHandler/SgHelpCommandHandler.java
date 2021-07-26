package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.BranchCommandHelpNew;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.commadHelp.RootCommandHelpNew;
import tokyo.nakanaka.logger.LogDesignColor;
import tokyo.nakanaka.logger.LogTemplate;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class SgHelpCommandHandler implements CommandHandler {
	private SgCommandDirectory sgCmdDir;
	private BranchCommandHelp cmdHelp;
	
	public SgHelpCommandHandler(SgCommandDirectory sgCmdDir) {
		this.sgCmdDir = sgCmdDir;
		this.cmdHelp = new BranchCommandHelp.Builder("help")
				.description("Print command help")
				.addParameter(ParameterType.OPTIONAL, "subcommand")
				.build();
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

	public List<String> onTabComplete(Player player, String[] args) {
		return onRecursiveTabComplete(this.sgCmdDir, player, args);
	}
	
	private static List<String> onRecursiveTabComplete(CommandEntry cmdEntry, Player player, String[] args) {
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
				return onRecursiveTabComplete(subEntry, player, subArgs);
			}
		}else {
			return new ArrayList<>();
		}
	}
	
}
