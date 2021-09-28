package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.command.MirrorCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.CommandLogColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles "/sg mirror" command
 */
public class MirrorCommandHandler implements SubCommandHandler {
	private static final CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length != 1) {
			player.print(cmdLogColor.error() + "Usage: /sg mirror <x|y|z>");
			return;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(cmdLogColor.error() + "Can not parse axis");
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
		MirrorCommand mirrorCmd = new MirrorCommand(originalCmd, axis, playerData.getBlockPhysics());
		mirrorCmd.execute();
		undoManager.add(mirrorCmd);
		player.print(cmdLogColor.main() + "Mirrored along the " + axis.toString().toLowerCase() + " axis");
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> List.of("x", "y", "z");
			default -> new ArrayList<>();
		};
	}
	
}
