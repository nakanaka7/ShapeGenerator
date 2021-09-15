package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import org.bukkit.Axis;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.command.MaxXCommand;
import tokyo.nakanaka.shapeGenerator.command.MaxYCommand;
import tokyo.nakanaka.shapeGenerator.command.MaxZCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

/**
 * Handles "sg max" subcommand
 */
public class MaxCommandHandler implements SubCommandHandler {

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		//check args length
		if(args.length != 2) {
			player.print(LogColor.RED + "Usage: " + "max x|y|z <coordinate>");
			return;
		}
		Axis axis;
		try {
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Can not parse axis");
			return;
		}
		double coord;
		try {
			coord = Double.valueOf(args[1]);
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Can not parse coordinate");
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
		UndoableCommand maxCmd = switch(axis) {
			case X -> new MaxXCommand(originalCmd, coord, playerData.getBlockPhysics());
			case Y -> new MaxYCommand(originalCmd, coord, playerData.getBlockPhysics());
			case Z -> new MaxZCommand(originalCmd, coord, playerData.getBlockPhysics());
		};
		maxCmd.execute();
		undoManager.add(maxCmd);
		player.print(LogColor.GOLD + "Set max" + axis.toString().toUpperCase() + " -> " + coord);
		return;
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
