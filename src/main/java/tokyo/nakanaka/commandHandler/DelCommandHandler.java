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
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class DelCommandHandler implements SubCommandHandler{
	private CommandHelp help = new CommandHelp.Builder("del")
			.description("Delete the generated blocks")
			.build();

	private String usage = "/sg del";
	
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
