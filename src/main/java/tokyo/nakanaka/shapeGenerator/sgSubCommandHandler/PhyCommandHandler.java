package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.PhyHelp;

/**
 * Handles "/sg phy" command
 */
public class PhyCommandHandler implements SubCommandHandler {
	private static final CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		String usageMsg = cmdLogColor.error() + "Usage: " + new PhyHelp().getUsage();
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
		player.print(cmdLogColor.main() + "Set physics -> " + bool);
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> List.of("true", "false");
			default -> List.of();
		};
	}

}
