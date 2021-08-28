package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.DeleteCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.commandHelp.DelHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg del" command
 */
public class DelCommandHandler implements UserCommandHandler {
	@Override
	public String getLabel() {
		return "del";
	}
		
	@Override
	public String getDescription() {
		return "Delete the generated blocks";
	}
	
	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		if(args.length > 1) {
			player.print(LogColor.RED + "Usage: " + new DelHelp().getUsage());
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
		UndoCommandManager undoManager = userData.getUndoCommandManager();
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
			player.print(LogDesignColor.ERROR + "Generate blocks first");
			return;
		}
		player.print(LogDesignColor.NORMAL + "Deleted " + delNum + " generation(s)");
		if(delNum < num) {
			player.print(LogDesignColor.ERROR + "reached the first generation");
		}
		return;
	}
	
	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}
		return new ArrayList<>();
	}
	
}
