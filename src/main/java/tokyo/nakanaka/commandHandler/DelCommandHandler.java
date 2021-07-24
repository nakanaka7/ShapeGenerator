package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.DeleteCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogDesignColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class DelCommandHandler implements CommandHandler {
	private BranchCommandHelp cmdHelp;
	
	public DelCommandHandler() {
		this.cmdHelp = new BranchCommandHelp.Builder("del")
				.description("Delete the generated blocks")
				.addParameter(ParameterType.OPTIONAL, "number")
				.build();
	}

	@Override
	public String getLabel() {
		return "del";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.OPTIONAL, "number", ""));
		return list;
	}
	
	@Override
	public CommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}
	
	@Override
	public String getDescription() {
		return "Delete the generated blocks";
	}
	
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length > 1) {
			logger.print(LogDesignColor.ERROR + "Usage: " + "/sg " + this.cmdHelp.getUsage());
			return;
		}
		int num = 1;
		if(args.length == 1) {
			try {
				num = Integer.parseInt(args[0]);
			}catch(IllegalArgumentException e) {
				logger.print(LogDesignColor.ERROR + "Can not parse the number");
				return;
			}
			if(num <= 0) {
				logger.print(LogDesignColor.ERROR + "The number must be larger than 0");
				return;
			}
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
		List<GenerateCommand> originalList = new ArrayList<>();
		for(int i = undoManager.undoSize() - 1; i >= 0; --i) {
			UndoableCommand cmd = undoManager.getUndoCommand(i);
			GenerateCommand originalCmd = null;
			if(cmd instanceof GenerateCommand) {
				originalCmd = (GenerateCommand) cmd;
			}else if(cmd instanceof AdjustCommand) {
				originalCmd = ((AdjustCommand)cmd).getLastCommand();
			}
			if(originalCmd != null && !originalCmd.hasUndone()) {
				originalList.add(originalCmd);
				if(originalList.size() == num) {
					break;
				}
			}
		}
		int delNum = originalList.size();
		DeleteCommand deleteCmd
			= new DeleteCommand(originalList.toArray(new GenerateCommand[delNum]));
		deleteCmd.execute();
		undoManager.add(deleteCmd);
		if(delNum == 0) {
			logger.print(LogDesignColor.ERROR + "Generate blocks first");
			return;
		}
		logger.print(LogDesignColor.NORMAL + "Deleted " + delNum + " chunk(s)");
		if(delNum < num) {
			logger.print(LogDesignColor.NORMAL + "reached the first generation");
		}
		return;
	}
	
	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}
		return new ArrayList<>();
	}
	
}
