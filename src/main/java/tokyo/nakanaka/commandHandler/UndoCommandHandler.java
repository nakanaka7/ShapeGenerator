package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static tokyo.nakanaka.logger.LogConstant.*;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.command.UndoableCommand;

public class UndoCommandHandler implements CommandHandler{

	@Override
	public String getLabel() {
		return "undo";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("undo");
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 0) {
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
		}else {
			return false;
		}
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		return new ArrayList<>();
	}

}
