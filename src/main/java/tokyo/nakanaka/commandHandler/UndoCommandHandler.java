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
	private CommandHelp help = new CommandHelp.Builder("undo")
			.description("Undo a block changing command")
			.build();
	private String usage = "/sg undo";
	
	@Override
	public CommandHelp getCommandHelp() {
		return this.help;
	}
	
	@Override
	public String getLabel() {
		return "undo";
	}
	
	@Override
	public String getDescription() {
		return "Undo a block changing command";
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
