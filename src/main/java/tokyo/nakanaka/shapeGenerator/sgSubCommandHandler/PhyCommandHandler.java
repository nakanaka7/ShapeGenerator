package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.commandHelp.PhyHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg phy" command
 */
public class PhyCommandHandler implements SgSubCommandHandler {

	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		String usageMsg = LogColor.RED + "Usage: " + new PhyHelp().getUsage();
		if(args.length != 1) {
			player.print(usageMsg);
			return;
		}
		boolean physics;
		String bool = args[0];
		if(bool.equals("true")) {
			physics = true;
		}else if(bool.equals("false")) {
			physics = false;
		}else {
			player.print(usageMsg);
			return;
		}
		userData.setBlockPhysics(physics);
		player.print(LogColor.GOLD + "Set physics -> " + bool);
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		if(args.length == 1) {
			return List.of("true", "false");
		}else {
			return List.of();
		}
	}

}
