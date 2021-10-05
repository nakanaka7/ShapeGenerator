package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.BranchCommandHandler;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ParameterUsage;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgBranchHelpConstants;

import java.util.List;

/**
 * Handles "/sg phy" command
 */
public class PhyCommandHandler implements BranchCommandHandler {
	private static final CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);

	@Override
	public String label() {
		return "phy";
	}

	@Override
	public String description() {
		return "Toggle physics option for generating blocks";
	}

	@Override
	public ParameterUsage[] parameterUsages() {
		var tf = new ParameterUsage("true|false", "an option for physics");
		return new ParameterUsage[]{tf};
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		String usageMsg = cmdLogColor.error() + "Usage: " + "/sg " + SgBranchHelpConstants.PHY.syntax();
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
