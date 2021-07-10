package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class UndoCommandHandler implements CommandHandler{
	private BranchCommandHelp cmdHelp;
	
	public UndoCommandHandler() {
		this.cmdHelp = new BranchCommandHelp.Builder("undo")
				.description("Undo block changing command(s)")
				.addParameter(ParameterType.OPTIONAL, "number")
				.build();
	}
	
	@Override
	public String getLabel() {
		return "undo";
	}
	
	@Override
	public BranchCommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length > 1) {
			logger.print(LogColor.RED + "Usage: " + "/sg " + this.cmdHelp.getUsage());
			return;
		}
		int num = 1;
		if(args.length == 1) {
			try {
				num = Integer.parseInt(args[0]);
			}catch(IllegalArgumentException e) {
				logger.print(LogColor.RED + "Can not parse the number");
				return;
			}
			if(num <= 0) {
				logger.print(LogColor.RED + "The number must be larger than 0");
				return;
			}
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
		int i = 0;
		int totalNum = 0;
		while(i < num) {
			UndoableCommand undoCmd = undoManager.getUndoCommand();
			if(undoCmd == null) {
				break;
			}
			undoCmd.undo();
			++i;
			++totalNum;
		}
		if(totalNum == 0) {
			logger.print(LogColor.RED + "Nothing to undo");
			return;
		}
		logger.print(LogColor.DARK_AQUA + "Undid " + totalNum + " command(s)");
		if(totalNum < num) {
			logger.print(LogColor.RED + "Reached the beginning command");
		}
		return;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}
		return new ArrayList<>();
	}

}
