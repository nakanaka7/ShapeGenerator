package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.RotateCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg rot" command
 */
public class RotCommandHandler implements UserCommandHandler{
		
	@Override
	public String getLabel() {
		return "rot";
	}
	
	@Override
	public String getDescription() {
		return "Rotate the generated blocks";
	}
		
	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		if(args.length != 2) {
			player.print(LogColor.RED + "Usage: /sg rot <x|y|z> <degree>");
			return;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(LogDesignColor.ERROR + "Can not parse axis");
			return;
		}
		double degree;
		try {
			degree = Double.valueOf(args[1]);
		}catch(IllegalArgumentException e) {
			player.print(LogDesignColor.ERROR + "Can not parse double");
			return;
		}
		UndoCommandManager undoManager = userData.getUndoCommandManager();
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
			player.print(LogDesignColor.ERROR + "Generate blocks first");
			return;
		}
		RotateCommand rotateCmd = new RotateCommand(originalCmd, axis, degree, userData.getBlockPhysics());
		rotateCmd.execute();
		undoManager.add(rotateCmd);
		player.print(LogDesignColor.NORMAL + "Rotated " + degree + " degrees about the " + axis.toString().toLowerCase() + " axis");
		return;
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("x", "y", "z");
		}else if(args.length == 2) {
			return Arrays.asList("0", "90", "-90", "180", "270");
		}else {
			return new ArrayList<>();
		}
	}

}
