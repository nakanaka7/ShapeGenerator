package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.command.RotateCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ParameterUsage;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgBranchHelpConstants;

import java.util.List;

/**
 * Handles "/sg rot" command
 */
public class RotCommandHandler implements SubCommandHandler {

	@Override
	public String label() {
		return "rot";
	}

	@Override
	public String description() {
		return "Rotate the generated block(s)";
	}

	public ParameterUsage[] parameterUsages() {
		var xyz = new ParameterUsage("x|y|z", "an axis for rotating");
		var deg = new ParameterUsage("<degree>", "a degree for rotating");
		return new ParameterUsage[]{xyz, deg};
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
		String usage = Main.SG + " " + SgSublabel.ROT + " " + String.join(" ", SgBranchHelpConstants.ROT.parameterSyntaxes());
		if(args.length != 2) {
			player.print(cmdLogColor.error() + "Usage: " + usage);
			return;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(cmdLogColor.error() + "Can not parse axis");
			return;
		}
		double degree;
		try {
			degree = Double.valueOf(args[1]);
		}catch(IllegalArgumentException e) {
			player.print(cmdLogColor.error() + "Can not parse double");
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
		RotateCommand rotateCmd = new RotateCommand(originalCmd, axis, degree, playerData.getBlockPhysics());
		rotateCmd.execute();
		undoManager.add(rotateCmd);
		player.print(cmdLogColor.main() + "Rotated " + degree + " degrees about the " + axis.toString().toLowerCase() + " axis");
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> List.of("x", "y", "z");
			case 2 -> List.of("0.0", "90.0", "-90.0", "180.0", "270.0");
			default -> List.of();
		};
	}

}
