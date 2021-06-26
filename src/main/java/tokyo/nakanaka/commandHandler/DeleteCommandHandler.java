package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.DeleteCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.player.Player;

public class DeleteCommandHandler implements CommandHandler{

	@Override
	public CommandHelp getCommandHelp() {
		return new CommandHelp.Builder("del")
				.description("Delete the generated blocks")
				.build();
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
