package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.SgRootCommandHelpFactory;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.commadHelp.RootCommandHelp;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.LogDesignColor;
import tokyo.nakanaka.logger.LogTemplate;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class SgHelpCommandHandler implements CommandHandler {
	private BranchCommandHelp cmdHelp;
	private SgRootCommandHelpFactory helpFactory;
	
	public SgHelpCommandHandler(SgCommandDirectory sgCmdDir) {
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
		
	private void onCommand(CommandHelp help, Player player, String[] args) {
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
	
	@Override
	public void onCommand(Player player, String[] args) {
		RootCommandHelp help = this.helpFactory.create(player);
		this.onCommand(help, player, args);
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
