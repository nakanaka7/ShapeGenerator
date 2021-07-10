package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class RedoCommandHandler implements CommandHandler{
	private BranchCommandHelp cmdHelp;
	
	public RedoCommandHandler() {
		this.cmdHelp = new BranchCommandHelp.Builder("redo")
				.description("Redo block changing command")
				.build();
	}
	
	@Override
	public String getLabel() {
		return "redo";
	}
	
	@Override
	public BranchCommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 0) {
			logger.print(LogColor.RED + "Usage: " + "/sg " + this.cmdHelp.getUsage());
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
