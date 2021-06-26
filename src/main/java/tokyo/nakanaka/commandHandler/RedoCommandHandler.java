package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;
import static tokyo.nakanaka.logger.LogConstant.HEAD_NORMAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.player.Player;

public class RedoCommandHandler implements CommandHandler{
	
	@Override
	public CommandHelp getCommandHelp() {
		return new CommandHelp.Builder("redo")
				.description("Redo a block changing command")
				.build();
	}
	
	@Override
	public String getDescription() {
		return "Redo a block changing command";
	}
	
	@Override
	public String getLabel() {
		return "redo";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("redo");
	}
	
	@Override
	public String getUsage() {
		return "redo";
	}
	
	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		return new ArrayList<>();
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 0) {
			UndoCommandManager undoManager = player.getUndoCommandManager();
			UndoableCommand redoCmd = undoManager.getRedoCommand();
			if(redoCmd == null) {
				player.getLogger().print(HEAD_ERROR + "Nothing to redo");
				return true;
			}else {
				redoCmd.redo();
				player.getLogger().print(HEAD_NORMAL + "Redid 1 command");
				return true;
			}
		}else {
			return false;
		}
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		return new ArrayList<>();
	}

}
