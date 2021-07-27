package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.ShiftCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.geometricProperty.Direction;
import tokyo.nakanaka.logger.LogDesignColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.player.Player;

public class ShiftCommandHandler implements CommandHandler{

	@Override
	public String getLabel() {
		return "shift";
	}
	
	@Override
	public String getDescription() {
		return "Shift the generated blocks";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.REQUIRED, "direction", ""));
		list.add(new ParameterHelp(ParameterType.REQUIRED, "length", ""));
		return list;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 2) {
			return false;
		}
		Direction dir;
		double blocks;
		try {
			dir = Direction.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			logger.print(LogDesignColor.ERROR + "Can not parse direction");
			return true;
		}
		try {
			blocks = Double.parseDouble(args[1]);
		}catch(IllegalArgumentException e) {
			logger.print(LogDesignColor.ERROR + "Can not parse integer");
			return true;
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
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
			logger.print(LogDesignColor.ERROR + "Generate blocks first");
			return true;
		}
		double dx = dir.getX() * blocks;
		double dy = dir.getY() * blocks;
		double dz = dir.getZ() * blocks;
		Vector3D displacement = new Vector3D(dx, dy, dz);
		ShiftCommand shiftCmd = new ShiftCommand(originalCmd, displacement, player.getBlockPhysics());
		shiftCmd.execute();
		undoManager.add(shiftCmd);
		logger.print(LogDesignColor.NORMAL + "Shifted block(s) " + blocks + " " + dir.toString().toLowerCase());
		return true;
	}
	
	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList(Direction.values()).stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
		}else if(args.length == 2) {
			return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}else {
			return Arrays.asList();
		}
	}
	
}
