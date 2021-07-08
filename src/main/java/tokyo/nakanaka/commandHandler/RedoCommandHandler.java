package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class RedoCommandHandler implements SgSubCommandHandler{
	private CommandHelp cmdHelp;
	
	public RedoCommandHandler() {
		String desc = "Redo a block changing command";
		String usage = "/sg redo";
		this.cmdHelp = new CommandHelp("redo", desc, usage);
	}
	
	@Override
	public String getLabel() {
		return "redo";
	}
	
	@Override
	public CommandHelp getCommandHelp() {
		return this.cmdHelp;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 0) {
			logger.print(LogColor.RED + "Usage: " + this.cmdHelp.getUsage());
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
