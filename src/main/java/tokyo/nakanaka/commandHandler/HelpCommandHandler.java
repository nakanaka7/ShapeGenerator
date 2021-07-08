package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogColor.GOLD;
import static tokyo.nakanaka.logger.LogColor.RESET;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class HelpCommandHandler implements SgSubCommandHandler{
	private SgSubCommandHandlerRepository cmdRepo;
	private CommandHelp cmdHelp;
	
	public HelpCommandHandler(SgSubCommandHandlerRepository cmdRepo) {
		this.cmdRepo = cmdRepo;
		String desc = "Print command help";
		String usage = "/sg help [command]";
		this.cmdHelp = new CommandHelp(desc, usage);
	}

	@Override
	public String getLabel() {
		return "help";
	}
	
	public CommandHelp getCommandHelp() {
		return this.cmdHelp;
	}
	
	@Override
	public String getDescription() {
		return this.cmdHelp.getDescription();
	}
	
	@Override
	public String getUsage() {
		return this.cmdHelp.getUsage();
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
				logger.print(GOLD + "/sg " + handler.getLabel() + ": " + RESET + handler.getDescription());
			}
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
			logger.print(LogColor.GOLD + "Description: " + LogColor.RESET + cmdHandler.getDescription());
			logger.print(LogColor.GOLD + "Usage: " + LogColor.RESET + cmdHandler.getUsage());
			if(args[0].equals("sel")) {
				logger.print(LogColor.GOLD + "SubCommands:");
				SelCommandHandler selHandler = (SelCommandHandler)cmdHandler;
				List<Pair<String, String>> desList = selHandler.getSubCommandDescriptions(player);
				for(Pair<String, String> pair : desList) {
					logger.print(LogColor.GOLD + " /sg sel " +  pair.getFirst() + ": " + LogColor.RESET + pair.getSecond());
				}
			}
			return;
		}else if(args.length == 2) {
			if(args[0].equals("sel")) {
				SelCommandHandler selHandler = (SelCommandHandler) this.cmdRepo.findBy("sel");
				List<String> subLabels = selHandler.getSubCommandLabels(player);
				if(subLabels.contains(args[1])) {
					CommandHelp cmdHelp = selHandler.getSubCommandHelp(player, args[1]);
					logger.print(LogColor.YELLOW + "---------| " + LogColor.RESET
							+ "Help: " 
							+ LogColor.GOLD + "/sg sel " + args[1] + LogColor.RESET
							+ LogColor.YELLOW + " |---------------");
					logger.print(LogColor.GOLD + "Description: " + LogColor.RESET + cmdHelp.getDescription());
					logger.print(LogColor.GOLD + "Usage: " + LogColor.RESET + cmdHelp.getUsage());
				}else {
					logger.print(LogColor.RED + "Unkonwn command");
				}
				return;
			}
		}
		logger.print(LogColor.RED + "Usage: " + this.cmdHelp.getUsage());
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
