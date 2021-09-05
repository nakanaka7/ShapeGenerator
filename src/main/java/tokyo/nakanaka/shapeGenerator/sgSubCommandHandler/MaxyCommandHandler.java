package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.MaxYCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.MaxyHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg maxy" command
 */
public class MaxyCommandHandler implements SubCommandHandler {	
	
	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		if(args.length != 1) {
			player.print(LogColor.RED + "Usage: " + new MaxyHelp().getUsage());
			return;
		}
		double value;
		try {
			value = Double.valueOf(args[0]);
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
		MaxYCommand maxyCmd = new MaxYCommand(originalCmd, value, userData.getBlockPhysics());
		maxyCmd.execute();
		undoManager.add(maxyCmd);
		player.print(LogDesignColor.NORMAL + "Set maxY -> " + value);
		return;
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		return switch(args.length) {
		case 1 -> List.of(String.valueOf((double)player.getBlockPosition().y()));
		default -> List.of();
		};
	}

}
