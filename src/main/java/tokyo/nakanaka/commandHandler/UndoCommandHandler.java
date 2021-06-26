package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static tokyo.nakanaka.logger.LogConstant.*;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.player.Player;

public class UndoCommandHandler implements CommandHandler{
	
	@Override
	public CommandHelp getCommandHelp() {
		return new CommandHelp.Builder("undo")
				.description("Undo a block changing command")
				.build();
	}
	
	@Override
	public String getDescription() {
		return  "Undo a block changing command";
	}
	
	@Override
	public String getLabel() {
		return "undo";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("undo");
	}
	
	@Override
	public String getUsage() {
		return "undo";
	}

	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		return new ArrayList<>();
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
