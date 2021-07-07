package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class RedoCommandHandler implements SgSubCommandHandler{
	private String usage = "/sg redo";
	
	@Override
	public String getLabel() {
		return "redo";
	}
	
	@Override
	public String getDescription() {
		return "Redo a block changing command";
	}
	
	@Override
	public String getUsage() {
		return this.usage;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 0) {
			logger.print(LogColor.RED + "Usage: " + this.usage);
			return;
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
		UndoableCommand redoCmd = undoManager.getRedoCommand();
		if(redoCmd == null) {
			logger.print(LogColor.RED + "Nothing to redo");
			return;
		}else {
			redoCmd.redo();
			logger.print("Redid 1 command");
			return;
		}
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		return new ArrayList<>();
	}

}
