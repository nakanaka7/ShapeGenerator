package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.logger.LogDesignColor;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

/**
 * Handles "/sg redo" command
 */
public class RedoCommandHandler implements SubCommandHandler{
		
	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		String usageMsg = LogColor.RED + "Usage: /sg redo [number]";
		if(args.length > 1) {
			player.print(usageMsg);
			return;
		}
		int num = 1;
		if(args.length == 1) {
			try {
				num = Integer.parseInt(args[0]);
			}catch(IllegalArgumentException e) {
				player.print(LogDesignColor.ERROR + "Can not parse the number");
				return;
			}
			if(num <= 0) {
				player.print(LogDesignColor.ERROR + "The number must be larger than 0");
				return;
			}
		}
		UndoCommandManager undoManager = playerData.getUndoCommandManager();
		int totalNum = 0;
		for(int i = 0; i < num; ++i) {
			boolean success = undoManager.redo();
			if(!success) {
				break;
			}
			++totalNum;
		}
		if(totalNum == 0) {
			player.print(LogDesignColor.ERROR + "Nothing to redo");
			player.print(usageMsg);
			return;
		}
		player.print(LogDesignColor.NORMAL + "Redid " + totalNum + " command(s)");
		if(totalNum < num) {
			player.print(LogDesignColor.ERROR + "Reached the end command");
		}
		player.print(usageMsg);
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}
		return new ArrayList<>();
	}

}
