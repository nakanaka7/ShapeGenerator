package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.CommandHelpMessenger;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class SgCommandHandler {
	private SubCommandHandlerRepository cmdHandlerRepo;
	
	public SgCommandHandler(SubCommandHandlerRepository cmdHandlerRepo) {
		this.cmdHandlerRepo = cmdHandlerRepo;
	}
	
	public void onHelp(Logger logger) {
		new CommandHelpMessenger().onSgHelp(logger, this.cmdHandlerRepo);
	}
	
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		String helpMsg = "See help by " + LogColor.GOLD + "/sg help";
		if(args.length == 0) {
			logger.print(helpMsg);
			return;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		SubCommandHandler cmdHandler = this.cmdHandlerRepo.findBy(label);
		if(cmdHandler == null) {
			logger.print(helpMsg);
			return;
		}
		boolean success = cmdHandler.onCommand(player, shiftArgs);
		if(!success) {
			CommandHelp help = cmdHandler.getCommandHelp();
			for(String line : help.getHelp()) {
				player.getLogger().print(line);
			}
		}
	}
	
	public List<String> onTabComplete(Player player, String[] args){
		if(args.length == 1) {
			return new ArrayList<>(this.cmdHandlerRepo.getAliases());
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		SubCommandHandler cmdHandler = this.cmdHandlerRepo.findBy(label);
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(player, shiftArgs);
		}else {
			return new ArrayList<>();
		}
	}
		
}
