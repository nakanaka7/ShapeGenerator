package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.user.UserOld;

public class RedoCommandHandler implements UserCommandHandler{
		
	@Override
	public String getLabel() {
		return "redo";
	}
	
	@Override
	public String getDescription() {
		return "Redo block changing command(s)";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.OPTIONAL, "number", ""));
		return list;
	}
	
	@Override
	public void onCommand(UserOld user, String[] args) {
		Logger logger = user.getLogger();
		String usageMsg = LogColor.RED + "Usage: /sg redo [number]";
		if(args.length > 1) {
			logger.print(usageMsg);
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
		UndoCommandManager undoManager = user.getUndoCommandManager();
		int totalNum = 0;
		for(int i = 0; i < num; ++i) {
			boolean success = undoManager.redo();
			if(!success) {
				break;
			}
			++totalNum;
		}
		if(totalNum == 0) {
			logger.print(LogDesignColor.ERROR + "Nothing to redo");
			logger.print(usageMsg);
			return;
		}
		logger.print(LogDesignColor.NORMAL + "Redid " + totalNum + " command(s)");
		if(totalNum < num) {
			logger.print(LogDesignColor.ERROR + "Reached the end command");
		}
		logger.print(usageMsg);
	}

	@Override
	public List<String> onTabComplete(UserOld user, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}
		return new ArrayList<>();
	}

}
