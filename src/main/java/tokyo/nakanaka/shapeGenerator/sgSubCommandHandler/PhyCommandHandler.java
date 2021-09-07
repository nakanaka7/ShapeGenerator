package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.PhyHelp;

/**
 * Handles "/sg phy" command
 */
public class PhyCommandHandler implements SubCommandHandler {

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
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
		playerData.setBlockPhysics(physics);
		player.print(LogColor.GOLD + "Set physics -> " + bool);
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		if(args.length == 1) {
			return List.of("true", "false");
		}else {
			return List.of();
		}
	}

}
