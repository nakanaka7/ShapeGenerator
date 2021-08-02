package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.player.User;

public class UndoCommandHandler implements CommandHandler{
	
	@Override
	public String getLabel() {
		return "undo";
	}
	
	@Override
	public String getDescription() {
		return "Undo block changing command(s)";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.OPTIONAL, "number", ""));
		return list;
	}
	
	@Override
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
		int totalNum = 0;
		for(int i = 0; i < num; ++i) {
			boolean success = undoManager.undo();
			if(!success) {
				break;
			}
			++totalNum;
		}
		if(totalNum == 0) {
			logger.print(LogDesignColor.ERROR + "Nothing to undo");
			return true;
		}
		logger.print(LogDesignColor.NORMAL + "Undid " + totalNum + " command(s)");
		if(totalNum < num) {
			logger.print(LogDesignColor.ERROR + "Reached the beginning command");
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
