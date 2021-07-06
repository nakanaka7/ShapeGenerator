package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;
import static tokyo.nakanaka.logger.LogConstant.HEAD_NORMAL;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class UndoCommandHandler implements SubCommandHandler{
	private CommandHelp help = new CommandHelp.Builder("undo")
			.description("Undo a block changing command")
			.build();
	private String usage = "/sg undo";
	
	@Override
	public CommandHelp getCommandHelp() {
		return this.help;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 0) {
			logger.print(LogColor.RED + "Usage: " + this.usage);
			return true;
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
		UndoableCommand undoCmd = undoManager.getUndoCommand();
		if(undoCmd == null) {
			player.getLogger().print(HEAD_ERROR + "Nothing to undo");
			return true;
		}else {
			undoCmd.undo();
			player.getLogger().print(HEAD_NORMAL + "Undid 1 command");
			return true;
		}
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		return new ArrayList<>();
	}

}
