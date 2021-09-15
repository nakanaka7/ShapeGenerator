package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.DeleteCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.DelHelp;

/**
 * Handles "/sg del" command
 */
public class DelCommandHandler implements SubCommandHandler {
	
	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length > 1) {
			player.print(LogColor.RED + "Usage: " + new DelHelp().getUsage());
			return;
		}
		int num = 1;
		if(args.length == 1) {
			try {
				num = Integer.parseInt(args[0]);
			}catch(IllegalArgumentException e) {
				player.print(LogColor.RED + "Can not parse the number");
				return;
			}
			if(num <= 0) {
				player.print(LogColor.RED + "The number must be larger than 0");
				return;
			}
		}
		UndoCommandManager undoManager = playerData.getUndoCommandManager();
		List<GenerateCommand> originalList = new ArrayList<>();
		for(int i = undoManager.undoSize() - 1; i >= 0; --i) {
			UndoableCommand cmd = undoManager.getUndoCommand(i);
			GenerateCommand originalCmd = null;
			if(cmd instanceof GenerateCommand) {
				originalCmd = (GenerateCommand) cmd;
			}else if(cmd instanceof AdjustCommand) {
				originalCmd = ((AdjustCommand)cmd).getLastCommand();
			}
			if(originalCmd != null && !originalCmd.hasUndone()) {
				originalList.add(originalCmd);
				if(originalList.size() == num) {
					break;
				}
			}
		}
		int delNum = originalList.size();
		DeleteCommand deleteCmd
			= new DeleteCommand(originalList.toArray(new GenerateCommand[delNum]));
		deleteCmd.execute();
		undoManager.add(deleteCmd);
		if(delNum == 0) {
			player.print(LogColor.RED + "Generate blocks first");
			return;
		}
		player.print(LogColor.GOLD + "Deleted " + delNum + " generation(s)");
		if(delNum < num) {
			player.print(LogColor.RED + "reached the first generation");
		}
		return;
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}
		return new ArrayList<>();
	}
	
}
