package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.BranchCommandHandler;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.command.*;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ParameterUsage;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgBranchHelpConstants;

/**
 * Handles "sg min" subcommand
 */
public class MinCommandHandler implements SubCommandHandler {
	private static final CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);

	@Override
	public String label() {
		return "min";
	}

	@Override
	public String description() {
		return "Set min coordinate of the generated blocks";
	}

	public ParameterUsage[] parameterUsages() {
		var xyz = new ParameterUsage("x|y|z", "axis");
		var coord = new ParameterUsage("<coordinate>", "minimum coordinate of the generation");
		return new ParameterUsage[]{xyz, coord};
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		//check args length
		if(args.length != 2) {
			player.print(cmdLogColor.error() + "Usage: " + "/sg " + SgBranchHelpConstants.MIN.syntax());
			return;
		}
		Axis axis;
		try {
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(cmdLogColor.error() + "Can not parse axis");
			return;
		}
		double coord;
		try {
			coord = Double.valueOf(args[1]);
		}catch(IllegalArgumentException e) {
			player.print(cmdLogColor.error() + "Can not parse coordinate");
			return;
		}
		UndoCommandManager undoManager = playerData.getUndoCommandManager();
		GenerateCommand originalCmd = null;
		for(int i = undoManager.undoSize() - 1; i >= 0; --i) {
			UndoableCommand cmd = undoManager.getUndoCommand(i);
			GenerateCommand genrCmd = null;
			if(cmd instanceof GenerateCommand) {
				genrCmd = (GenerateCommand) cmd;	
			}else if(cmd instanceof AdjustCommand) {
				genrCmd = ((AdjustCommand)cmd).getLastCommand();
			}
			if(genrCmd != null && !genrCmd.hasUndone()) {
				originalCmd = genrCmd;
				break;
			}
		}
		if(originalCmd == null) {
			player.print(cmdLogColor.error() + "Generate blocks first");
			return;
		}
		UndoableCommand minCmd = new MinCommand(originalCmd, axis, coord, playerData.getBlockPhysics());
		minCmd.execute();
		undoManager.add(minCmd);
		player.print(cmdLogColor.main() + "Set min" + axis.toString().toUpperCase() + " -> " + coord);
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
		case 1 -> List.of("x", "y", "z");
		case 2 -> {
			Axis axis;
			try {
				axis = Axis.valueOf(args[0].toUpperCase());
			}catch(IllegalArgumentException e) {
				yield List.of();
			}
			double s = switch(axis) {
			case X -> (double)player.getBlockPosition().x();
			case Y -> (double)player.getBlockPosition().y();
			case Z -> (double)player.getBlockPosition().z();
			};
			yield List.of(String.valueOf(s));
		}
		default -> List.of();
		};
	}

}
