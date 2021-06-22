package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.DeleteCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.UndoableCommand;

public class DeleteCommandHandler implements CommandHandler{

	@Override
	public String getDescription() {
		return "Delete the generated blocks";
	}

	@Override
	public String getLabel() {
		return "delete";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("del", "delete");
	}

	@Override
	public String getUsage() {
		return "delete/del";
	}

	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		return new ArrayList<>();
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length != 0) {
			return false;
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
		UndoableCommand cmd = undoManager.peekUndoCommand();
		GenerateCommand originalCmd;
		if(cmd instanceof GenerateCommand) {
			originalCmd = (GenerateCommand) cmd;
		}else if(cmd instanceof AdjustCommand) {
			originalCmd = ((AdjustCommand)cmd).getLastCommand();
		}else {
			player.getLogger().print(HEAD_ERROR + "Generate blocks first");
			return true;
		}
		DeleteCommand deleteCmd = new DeleteCommand(originalCmd);
		deleteCmd.execute();
		undoManager.add(deleteCmd);
		return true;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		return new ArrayList<>();
	}

}
