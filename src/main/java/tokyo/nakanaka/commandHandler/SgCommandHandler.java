package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class SgCommandHandler {
	private SgSubCommandHandlerRepository cmdHandlerRepo;
	
	public SgCommandHandler(SgSubCommandHandlerRepository cmdHandlerRepo) {
		this.cmdHandlerRepo = cmdHandlerRepo;
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
		BranchCommandHandler cmdHandler = this.cmdHandlerRepo.findBy(label);
		if(cmdHandler == null) {
			logger.print(helpMsg);
			return;
		}
		cmdHandler.onCommand(player, shiftArgs);
	}
	
	public List<String> onTabComplete(Player player, String[] args){
		if(args.length == 1) {
			return new ArrayList<>(this.cmdHandlerRepo.getAliases());
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		BranchCommandHandler cmdHandler = this.cmdHandlerRepo.findBy(label);
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(player, shiftArgs);
		}else {
			return new ArrayList<>();
		}
	}
		
}
