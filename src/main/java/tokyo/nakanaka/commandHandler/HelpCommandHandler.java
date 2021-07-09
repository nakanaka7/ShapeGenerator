package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.commadHelp.RootCommandHelp;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class HelpCommandHandler implements CommandHandler{
	private SgSubCommandHandlerRepository cmdRepo;
	private BranchCommandHelp cmdHelp;
	
	public HelpCommandHandler(SgSubCommandHandlerRepository cmdRepo) {
		this.cmdRepo = cmdRepo;
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
	public BranchCommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}
	
	private RootCommandHelp getSgCommandHelp(Player player) {
		RootCommandHelp rootCmdHelp = new RootCommandHelp.Builder("sg")
				.description("Root command for ShapeGenerator")
				.build();
		List<String> subLabelList = cmdRepo.getAliases();
		for(String subLabel : subLabelList) {
			rootCmdHelp.register(this.cmdRepo.findBy(subLabel).getCommandHelp(player));
		}
		return rootCmdHelp;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		RootCommandHelp sgHelp = this.getSgCommandHelp(player);
		CommandHelp help = sgHelp.getSubHelp(args);
		if(help == null) {
			logger.print(LogColor.RED + "Usage: " + "/sg " + this.cmdHelp.getUsage());
			return;
		}
		logger.print(LogColor.YELLOW + "---------| " + LogColor.RESET
				+ "Help: " 
				+ LogColor.GOLD + "/sg " + String.join(" ", args) + LogColor.RESET
				+ LogColor.YELLOW + " |-----------------");
		logger.print(LogColor.GOLD + "Description: " + LogColor.RESET + help.getDescription());
		String usage = "/sg " + String.join(" ", args);
		if(help instanceof RootCommandHelp) {
			usage += " " + "<subcommand>"; 
		}else if(help instanceof BranchCommandHelp) {
			usage += " " + ((BranchCommandHelp) help).getParameterUsage(); 
		}
		logger.print(LogColor.GOLD + "Usage: " + LogColor.RESET + usage);
		if(help instanceof RootCommandHelp) {
			logger.print(LogColor.GOLD + "SubCommand:");
			List<CommandHelp> subList = ((RootCommandHelp) help).getSubCommandHelp();
			for(CommandHelp e : subList) {
				logger.print(" " + LogColor.GOLD +e.getLabel() + ": " + LogColor.RESET + e.getDescription());
			}
			logger.print(LogColor.GREEN + "Type /sg help " + String.join(" ", args) + " <subcommand> " + "for detail");
		}
	}
	
	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return new ArrayList<>(this.cmdRepo.getAliases());
		}else if(args.length == 2 && args[0].equals("sel")) {
			SelCommandHandler selHandler = (SelCommandHandler) this.cmdRepo.findBy("sel");
			List<String> subLabels = selHandler.getSubCommandLabels(player);
			return subLabels;
		}else {
			return new ArrayList<>();
		}
	}

}
