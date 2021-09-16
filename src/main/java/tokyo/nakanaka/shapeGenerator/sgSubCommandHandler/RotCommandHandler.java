package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.command.RotateCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

/**
 * Handles "/sg rot" command
 */
public class RotCommandHandler implements SubCommandHandler{
			
	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length != 2) {
			player.print(LogColor.RED + "Usage: /sg rot <x|y|z> <degree>");
			return;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Can not parse axis");
			return;
		}
		double degree;
		try {
			degree = Double.valueOf(args[1]);
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Can not parse double");
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
			player.print(LogColor.RED + "Generate blocks first");
			return;
		}
		RotateCommand rotateCmd = new RotateCommand(originalCmd, axis, degree, playerData.getBlockPhysics());
		rotateCmd.execute();
		undoManager.add(rotateCmd);
		player.print(LogColor.GOLD + "Rotated " + degree + " degrees about the " + axis.toString().toLowerCase() + " axis");
		return;
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("x", "y", "z");
		}else if(args.length == 2) {
			return Arrays.asList("0.0", "90.0", "-90.0", "180.0", "270.0");
		}else {
			return new ArrayList<>();
		}
	}

}
