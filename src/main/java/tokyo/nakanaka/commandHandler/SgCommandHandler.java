package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.player.Player;

public class SgCommandHandler {
private CommandHandlerRepository cmdHandlerRepo;
	
	public SgCommandHandler(CommandHandlerRepository cmdHandlerRepo) {
		this.cmdHandlerRepo = cmdHandlerRepo;
	}
	
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 0) {
			return false;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		CommandHandler cmdHandler = this.cmdHandlerRepo.findBy(label);
		if(cmdHandler == null) {
			return false;
		}
		boolean success = cmdHandler.onCommand(player, shiftArgs);
		if(!success) {
			CommandHelp help = cmdHandler.getCommandHelp();
			for(String line : help.getHelp()) {
				player.getLogger().print(line);
			}
		}
		return true;
	}
	
	public List<String> onTabComplete(Player player, String[] args){
		if(args.length == 1) {
			return new ArrayList<>(this.cmdHandlerRepo.getAliases());
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		CommandHandler cmdHandler = this.cmdHandlerRepo.findBy(label);
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(player, shiftArgs);
		}else {
			return new ArrayList<>();
		}
	}
		
}
