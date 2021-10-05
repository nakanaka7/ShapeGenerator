package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Direction;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.command.ShiftCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ParameterUsage;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgBranchHelpConstants;

import java.util.List;

/**
 * Handles "/sg shift" command
 */
public class ShiftCommandHandler implements SubCommandHandler{
	private static final CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);

	@Override
	public String label() {
		return "shift";
	}

	@Override
	public String description() {
		return "Shift the generated blocks";
	}

	public ParameterUsage[] parameterUsages() {
		var dir = new ParameterUsage("<direction>", "the direction to shift");
		var dis = new ParameterUsage("<distance>", "distance of shift");
		return new ParameterUsage[]{dir, dis};
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length != 2) {
			player.print(cmdLogColor.error() + "Usage: " + "/sg " + SgBranchHelpConstants.SHIFT.syntax());
			return;
		}
		Direction dir;
		double blocks;
		try {
			dir = Direction.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(cmdLogColor.error() + "Can not parse direction");
			return;
		}
		try {
			blocks = Double.parseDouble(args[1]);
		}catch(IllegalArgumentException e) {
			player.print(cmdLogColor.error() + "Can not parse integer");
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
		double dx = dir.getX() * blocks;
		double dy = dir.getY() * blocks;
		double dz = dir.getZ() * blocks;
		Vector3D displacement = new Vector3D(dx, dy, dz);
		ShiftCommand shiftCmd = new ShiftCommand(originalCmd, displacement, playerData.getBlockPhysics());
		shiftCmd.execute();
		undoManager.add(shiftCmd);
		player.print(cmdLogColor.main() + "Shifted block(s) " + blocks + " " + dir.toString().toLowerCase());
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch (args.length) {
			case 1 -> List.of(Direction.values()).stream()
					.map(s -> s.toString().toLowerCase())
					.toList();
			case 2 -> List.of("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0",
					"5.0", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5");
			default -> List.of();
		};
	}
	
}
