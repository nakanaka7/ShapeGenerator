package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgSubcommandHelps;

import java.util.List;

/**
 * Handles "/sg undo" command
 */
public class UndoCommandHandler implements SubCommandHandler {

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
		String usage = Main.SG + " " + SgSublabel.UNDO + " " + String.join(" ", SgSubcommandHelps.UNDO.parameterSyntaxes());
		if(args.length > 1) {
			player.print(cmdLogColor.error() + "Usage: " + usage);
			return;
		}
		int num = 1;
		if(args.length == 1) {
			try {
				num = Integer.parseInt(args[0]);
			}catch(IllegalArgumentException e) {
				player.print(cmdLogColor.error() + "Can not parse the number");
				return;
			}
			if(num <= 0) {
				player.print(cmdLogColor.error() + "The number must be larger than 0");
				return;
			}
		}
		UndoCommandManager undoManager = playerData.getUndoCommandManager();
		int totalNum = 0;
		for(int i = 0; i < num; ++i) {
			boolean success = undoManager.undo();
			if(!success) {
				break;
			}
			++totalNum;
		}
		if(totalNum == 0) {
			player.print(cmdLogColor.error() + "Nothing to undo");
			return;
		}
		player.print(cmdLogColor.main() + "Undid " + totalNum + " command(s)");
		if(totalNum < num) {
			player.print(cmdLogColor.error() + "Reached the beginning command");
		}
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch (args.length) {
			case 1 -> List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");
			default -> List.of();
		};
	}

}
