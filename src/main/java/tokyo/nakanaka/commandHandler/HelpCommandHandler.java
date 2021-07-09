package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogColor.GOLD;
import static tokyo.nakanaka.logger.LogColor.RESET;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.commadHelp.RootCommandHelp;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class HelpCommandHandler implements SgSubCommandHandler{
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
	public BranchCommandHelp getCommandHelp() {
		return this.cmdHelp;
	}
	
	private RootCommandHelp getSgCommandHelp() {
		RootCommandHelp rootCmdHelp = new RootCommandHelp.Builder("sg")
				.description("Root command for ShapeGenerator")
				.build();
		List<String> subLabelList = cmdRepo.getAliases();
		for(String subLabel : subLabelList) {
			rootCmdHelp.register(this.cmdRepo.findBy(subLabel).getCommandHelp());
		}
		return rootCmdHelp;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length == 0) {
			logger.print(LogColor.YELLOW + "---------| " + LogColor.RESET
					+ "Help: " 
					+ LogColor.GOLD + "/sg" + LogColor.RESET
					+ LogColor.YELLOW + " |-----------------");
			for(SgSubCommandHandler handler : this.cmdRepo.getAll()) {
				logger.print(GOLD + "/sg " + handler.getLabel() + ": " + RESET + handler.getCommandHelp().getDescription());
			}
			logger.print("Type /sg help " + "<SubCommand> " + "for detail");
			return;
		}else if(args.length == 1) {
			SgSubCommandHandler cmdHandler = this.cmdRepo.findBy(args[0]);
			if(cmdHandler == null) {
				logger.print(LogColor.RED + "Unknown command");
				return;
			}
			logger.print(LogColor.YELLOW + "---------| " + LogColor.RESET
					+ "Help: " 
					+ LogColor.GOLD + "/sg " + cmdHandler.getLabel() + LogColor.RESET
					+ LogColor.YELLOW + " |---------------");
			logger.print(LogColor.GOLD + "Description: " + LogColor.RESET + cmdHandler.getCommandHelp().getDescription());
			logger.print(LogColor.GOLD + "Usage: " + LogColor.RESET + "/sg " + cmdHandler.getCommandHelp().getUsage());
			if(args[0].equals("sel")) {
				logger.print(LogColor.GOLD + "SubCommand:");
				SelCommandHandler selHandler = (SelCommandHandler)cmdHandler;
				List<Pair<String, String>> desList = selHandler.getSubCommandDescriptions(player);
				for(Pair<String, String> pair : desList) {
					logger.print(LogColor.GOLD + " " + pair.getFirst() + ": " + LogColor.RESET + pair.getSecond());
				}
				logger.print("Type /sg help " + "sel " + "<SubCommand> " + "for detail");
			}
			return;
		}else if(args.length == 2) {
			if(args[0].equals("sel")) {
				SelCommandHandler selHandler = (SelCommandHandler) this.cmdRepo.findBy("sel");
				List<String> subLabels = selHandler.getSubCommandLabels(player);
				if(subLabels.contains(args[1])) {
					BranchCommandHelp cmdHelp = selHandler.getSubCommandHelp(player, args[1]);
					logger.print(LogColor.YELLOW + "---------| " + LogColor.RESET
							+ "Help: " 
							+ LogColor.GOLD + "/sg sel " + args[1] + LogColor.RESET
							+ LogColor.YELLOW + " |---------------");
					logger.print(LogColor.GOLD + "Description: " + LogColor.RESET + cmdHelp.getDescription());
					logger.print(LogColor.GOLD + "Usage: " + LogColor.RESET + "/sg sel " + cmdHelp.getUsage());
				}else {
					logger.print(LogColor.RED + "Unkonwn command");
				}
				return;
			}
		}
		logger.print(LogColor.RED + "Usage: " + "/sg " + this.cmdHelp.getUsage());
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
