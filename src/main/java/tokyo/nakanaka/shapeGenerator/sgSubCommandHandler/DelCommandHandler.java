package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.DeleteCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ParameterUsage;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgBranchHelpConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles "/sg del" command
 */
public class DelCommandHandler implements SubCommandHandler {

	@Override
	public String label() {
		return "del";
	}

	@Override
	public String description() {
		return "Delete the generated block(s)";
	}

	public ParameterUsage[] parameterUsages() {
		var num = new ParameterUsage("[number]", "a number to delete generation(s)");
		return new ParameterUsage[]{num};
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
		if(args.length > 1) {
			player.print(cmdLogColor.error() + "Usage: " + "/sg " + SgBranchHelpConstants.DEL.syntax());
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
			player.print(cmdLogColor.error() + "Generate blocks first");
			return;
		}
		player.print(cmdLogColor.main() + "Deleted " + delNum + " generation(s)");
		if(delNum < num) {
			player.print(cmdLogColor.error() + "reached the first generation");
		}
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");
			default ->  List.of();
		};
	}
	
}
