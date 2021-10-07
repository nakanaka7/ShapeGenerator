package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.Main;
import tokyo.nakanaka.shapeGenerator.SgSublabel;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ParameterUsage;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgSubcommandHelps;

import java.util.List;

/**
 * Handles "/sg phy" command
 */
public class PhyCommandHandler implements SubCommandHandler {

	@Override
	public String label() {
		return "phy";
	}

	@Override
	public String description() {
		return "Toggle physics option for generating blocks";
	}

	public ParameterUsage[] parameterUsages() {
		var tf = new ParameterUsage("true|false", "an option for physics");
		return new ParameterUsage[]{tf};
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
		String usage = Main.SG + " " + SgSublabel.PHY + " " + String.join(" ", SgSubcommandHelps.PHY.parameterSyntaxes());
		String usageMsg = cmdLogColor.error() + "Usage: " + usage;
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
