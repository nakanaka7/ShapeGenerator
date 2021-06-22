package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Pair;
import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.ScaleCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.player.Player;

public class ScaleCommandHandler implements CommandHandler{

	@Override
	public String getDescription() {
		return "Change scale the generated blocks";
	}

	@Override
	public String getLabel() {
		return "scale";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("scale");
	}

	@Override
	public String getUsage() {
		return "scale x|y|z <scale factor>";
	}

	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		Pair<String, String> desAxis = new Pair<>("x|y|z", "scale axis");
		Pair<String, String> desDegree = new Pair<>("<scale factor>", "scale factor (double data type)");
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
		double factor;
		try {
			factor = Double.valueOf(args[1]);
		}catch(IllegalArgumentException e) {
			player.getLogger().print(HEAD_ERROR + "Can not parse double");
			return true;
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
		UndoableCommand cmd = undoManager.peekUndoCommand();
		GenerateCommand originalCmd;
		if(cmd instanceof GenerateCommand) {
			originalCmd = (GenerateCommand) cmd;
		}else if(cmd instanceof AdjustCommand) {
			originalCmd = ((AdjustCommand)cmd).getLastCommand();
		}else {
			player.getLogger().print(HEAD_ERROR + "Generate blocks first");
			return true;
		}
		ScaleCommand scaleCmd = new ScaleCommand(originalCmd, axis, factor, player.getBlockPhysics());
		scaleCmd.execute();
		undoManager.add(scaleCmd);
		return true;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("x", "y", "z");
		}else if(args.length == 2) {
			return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}else {
			return new ArrayList<>();
		}
	}

}
