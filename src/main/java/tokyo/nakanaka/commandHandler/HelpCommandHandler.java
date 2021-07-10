package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.commadHelp.RootCommandHelp;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class HelpCommandHandler implements CommandHandler {
	private CommandHelp help;
	private BranchCommandHelp cmdHelp;
	
	public HelpCommandHandler(CommandHelp help) {
		this.help = help;
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
	public CommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}

	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		String label = this.help.getLabel();
		CommandHelp subHelp = this.help.getSubHelp(args);
		if(subHelp == null) {
			logger.print(LogColor.RED + "Usage: " + "/" + label + " " + this.cmdHelp.getUsage());
			return;
		}
		logger.print(LogColor.YELLOW + "---------| " + LogColor.RESET
				+ "Help: " 
				+ LogColor.GOLD + "/" + label + " " + String.join(" ", args) + LogColor.RESET
				+ LogColor.YELLOW + " |-----------------");
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
				logger.print(" " + LogColor.GOLD +e.getLabel() + ": " + LogColor.RESET + e.getDescription());
			}
			logger.print(LogColor.GREEN + "Type " + "/" + label + " " + "help " + String.join(" ", args) + " <subcommand> " + "for detail");
		}
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		String subLabel = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		if(args.length == 1) {
			if(this.help instanceof RootCommandHelp) {
				List<CommandHelp> subHelpList = ((RootCommandHelp) help).getSubCommandHelp();
				return subHelpList.stream()
						.map(s -> s.getLabel())
						.collect(Collectors.toList());
			}else {
				return new ArrayList<>();
			}	
		}
		if(this.help instanceof RootCommandHelp) {
			CommandHelp subHelp = this.help.getSubHelp(subLabel);
			return new HelpCommandHandler(subHelp).onTabComplete(player, shiftArgs);
		}
		return new ArrayList<>();
	}

}
