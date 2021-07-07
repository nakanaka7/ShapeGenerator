package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogColor.GOLD;
import static tokyo.nakanaka.logger.LogColor.RESET;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class HelpCommandHandler implements SgSubCommandHandler{
	private SgSubCommandHandlerRepository cmdRepo;
	private String usage = "/sg help [command]";
	
	public HelpCommandHandler(SgSubCommandHandlerRepository cmdRepo) {
		this.cmdRepo = cmdRepo;
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
	public String getUsage() {
		return this.usage;
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
			if(cmdHandler instanceof SgSubRootCommandHandler) {
				logger.print(LogColor.GOLD + "SubCommands:");
				SgSubRootCommandHandler selHandler = (SgSubRootCommandHandler)cmdHandler;
				List<Pair<String, String>> desList = selHandler.getSubCommandDescriptions(player);
				for(Pair<String, String> pair : desList) {
					logger.print(LogColor.GOLD + " /sg sel " +  pair.getFirst() + ": " + LogColor.RESET + pair.getSecond());
				}
			}
			return;
		}else {
			logger.print(LogColor.RED + "Usage: " + this.usage);
			return;
		}	
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return new ArrayList<>(this.cmdRepo.getAliases());
		}else {
			return new ArrayList<>();
		}
	}

}
