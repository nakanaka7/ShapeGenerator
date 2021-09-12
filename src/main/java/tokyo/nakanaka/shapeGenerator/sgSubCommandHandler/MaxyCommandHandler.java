package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.command.MaxYCommand;
import tokyo.nakanaka.shapeGenerator.command.UndoableCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.MaxyHelp;

/**
 * Handles "/sg maxy" command
 */
public class MaxyCommandHandler implements SubCommandHandler {	
	
	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length != 1) {
			player.print(LogColor.RED + "Usage: " + new MaxyHelp().getUsage());
			return;
		}
		double value;
		try {
			value = Double.valueOf(args[0]);
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
		MaxYCommand maxyCmd = new MaxYCommand(originalCmd, value, playerData.getBlockPhysics());
		maxyCmd.execute();
		undoManager.add(maxyCmd);
		player.print(LogColor.GOLD + "Set maxY -> " + value);
		return;
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
		case 1 -> List.of(String.valueOf((double)player.getBlockPosition().y()));
		default -> List.of();
		};
	}

}
