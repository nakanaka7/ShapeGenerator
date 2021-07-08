package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class UndoCommandHandler implements SgSubCommandHandler{
	private CommandHelp cmdHelp;
	
	public UndoCommandHandler() {
		String desc = "Undo a block changing command";
		String usage = "/sg undo";
		this.cmdHelp = new CommandHelp("undo", desc, usage);
	}
	
	@Override
	public String getLabel() {
		return "undo";
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
