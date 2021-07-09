package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class UndoCommandHandler implements CommandHandler{
	private BranchCommandHelp cmdHelp;
	
	public UndoCommandHandler() {
		this.cmdHelp = new BranchCommandHelp.Builder("undo")
				.description("Undo a block changing command")
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
		if(args.length != 0) {
			logger.print(LogColor.RED + "Usage: " + "/sg " + this.cmdHelp.getUsage());
			return;
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
		UndoableCommand undoCmd = undoManager.getUndoCommand();
		if(undoCmd == null) {
			logger.print(LogColor.RED + "Nothing to undo");
			return;
		}else {
			undoCmd.undo();
			logger.print("Undid 1 command");
			return;
		}
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		return new ArrayList<>();
	}

}
