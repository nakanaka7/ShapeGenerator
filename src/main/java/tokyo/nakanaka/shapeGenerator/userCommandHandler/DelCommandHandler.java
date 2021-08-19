package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.DeleteCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.user.User;

public class DelCommandHandler implements UserCommandHandler {
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
	public String getDescription() {
		return "Delete the generated blocks";
	}
	
	public boolean onCommand(User user, String[] args) {
		Logger logger = user.getLogger();
		if(args.length > 1) {
			return false;
		}
		int num = 1;
		if(args.length == 1) {
			try {
				num = Integer.parseInt(args[0]);
			}catch(IllegalArgumentException e) {
				logger.print(LogDesignColor.ERROR + "Can not parse the number");
				return true;
			}
			if(num <= 0) {
				logger.print(LogDesignColor.ERROR + "The number must be larger than 0");
				return true;
			}
		}
		UndoCommandManager undoManager = user.getUndoCommandManager();
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
			return true;
		}
		logger.print(LogDesignColor.NORMAL + "Deleted " + delNum + " generation(s)");
		if(delNum < num) {
			logger.print(LogDesignColor.ERROR + "reached the first generation");
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(User user, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}
		return new ArrayList<>();
	}
	
}
