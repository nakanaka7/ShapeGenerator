package tokyo.nakanaka.commandHandler;

import java.util.Arrays;
import java.util.List;

import static tokyo.nakanaka.logger.LogConstant.*;

import tokyo.nakanaka.Direction;
import tokyo.nakanaka.Pair;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.MoveCommand;
import tokyo.nakanaka.command.ShiftCommand;
import tokyo.nakanaka.command.UndoableCommand;

public class ShiftCommandHandler implements CommandHandler{

	@Override
	public String getDescription() {
		return "Shift the generated blocks";
	}

	@Override
	public String getLabel() {
		return "shift";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("shift");
	}

	@Override
	public String getUsage() {
		return "shift <direction> <blocks>";
	}

	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		Pair<String, String> desDir = new Pair<>("<direction>", "direction to shift");
		Pair<String, String> desBlocks = new Pair<>("<blocks>", "block number to shift");
		return Arrays.asList(desDir, desBlocks);
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length != 2) {
			return false;
		}
		Direction dir;
		int blocks;
		try {
			dir = Direction.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.getLogger().print(HEAD_ERROR + "Can not parse direction");
			return true;
		}
		try {
			blocks = Integer.parseInt(args[1]);
		}catch(IllegalArgumentException e) {
			player.getLogger().print(HEAD_ERROR + "Can not parse integer");
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
		}else if(cmd instanceof MoveCommand) {
			originalCmd = ((MoveCommand)cmd).getLastCommand();
		}else {
			player.getLogger().print(HEAD_ERROR + "Invalid Operation");
			return true;
		}
		int dx = dir.getX() * blocks;
		int dy = dir.getY() * blocks;
		int dz = dir.getZ() * blocks;
		ShiftCommand shiftCmd = new ShiftCommand(originalCmd, dx, dy, dz, player.getBlockPhysics());
		shiftCmd.execute();
		undoManager.add(shiftCmd);
		return true;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length <= 3) {
			return Arrays.asList("-10", "-5", "0", "5", "10");
		}else {
			return Arrays.asList();
		}
	}

}
