package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.Parameter;
import tokyo.nakanaka.commadHelp.Parameter.Type;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.RotateCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.player.Player;

public class RotCommandHandler implements SubCommandHandler{

	@Override
	public CommandHelp getCommandHelp() {
		return new CommandHelp.Builder("rot")
				.description("Rotate the generated blocks")
				.addParameter(new Parameter(Type.REQUIRED, "x", "y", "z"), "rotation axis")
				.addParameter(new Parameter(Type.REQUIRED, "degree"), "angle of rotation (right-hand rule)")
				.build();
	}	
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length != 2) {
			return false;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.getLogger().print(HEAD_ERROR + "Can not parse axis");
			return true;
		}
		double degree;
		try {
			degree = Double.valueOf(args[1]);
		}catch(IllegalArgumentException e) {
			player.getLogger().print(HEAD_ERROR + "Can not parse double");
			return true;
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
		UndoableCommand cmd = undoManager.peekUndoCommand();
		if(cmd == null) {
			player.getLogger().print(HEAD_ERROR + "Generate blocks first");
			return true;
		}
		GenerateCommand originalCmd;
		if(cmd instanceof GenerateCommand) {
			originalCmd = (GenerateCommand) cmd;
		}else if(cmd instanceof AdjustCommand) {
			originalCmd = ((AdjustCommand)cmd).getLastCommand();
		}else {
			player.getLogger().print(HEAD_ERROR + "Invalid Operation");
			return true;
		}
		RotateCommand rotateCmd = new RotateCommand(originalCmd, axis, degree, player.getBlockPhysics());
		rotateCmd.execute();
		undoManager.add(rotateCmd);
		return true;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("x", "y", "z");
		}else if(args.length == 2) {
			return Arrays.asList("0", "90", "-90", "180", "270");
		}else {
			return new ArrayList<>();
		}
	}

}
