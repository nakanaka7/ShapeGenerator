package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Pair;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.RotateCommand;
import tokyo.nakanaka.command.UndoableCommand;

public class RotateCommandHandler implements CommandHandler{

	@Override
	public String getDescription() {
		return "Rotate the generated blocks";
	}

	@Override
	public String getLabel() {
		return "rotate";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("rotate");
	}

	@Override
	public String getUsage() {
		return "rotate x|y|z <degree>";
	}

	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		Pair<String, String> desAxis = new Pair<>("x|y|z", "rotation axis");
		Pair<String, String> desDegree = new Pair<>("<degree>", "angle of rotation (right-hand rule)");
		return Arrays.asList(desAxis, desDegree);
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
