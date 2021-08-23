package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.MaxXCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.commandHelp.MaxxHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class MaxXCommandHandler implements UserCommandHandler {	
	@Override
	public String getLabel() {
		return "maxx";
	}

	@Override
	public String getDescription() {
		return "Set max x of the generated blocks";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.REQUIRED, "value", "The x coordinate"));
		return list;
	}
	
	@Override
	public void onCommand(UserData user, String[] args) {
		Logger logger = user.getLogger();
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: " + new MaxxHelp().getUsage());
			return;
		}
		double value;
		try {
			value = Double.valueOf(args[0]);
		}catch(IllegalArgumentException e) {
			logger.print(LogDesignColor.ERROR + "Can not parse double");
			return;
		}
		UndoCommandManager undoManager = user.getUndoCommandManager();
		GenerateCommand originalCmd = null;
		for(int i = undoManager.undoSize() - 1; i >= 0; --i) {
			UndoableCommand cmd = undoManager.getUndoCommand(i);
			GenerateCommand genrCmd = null;
			if(cmd instanceof GenerateCommand) {
				genrCmd = (GenerateCommand) cmd;	
			}else if(cmd instanceof AdjustCommand) {
				genrCmd = ((AdjustCommand)cmd).getLastCommand();
			}
			if(genrCmd != null && !genrCmd.hasUndone()) {
				originalCmd = genrCmd;
				break;
			}
		}
		if(originalCmd == null) {
			logger.print(LogDesignColor.ERROR + "Generate blocks first");
			return;
		}
		MaxXCommand maxxCmd = new MaxXCommand(originalCmd, value, user.getBlockPhysics());
		maxxCmd.execute();
		undoManager.add(maxxCmd);
		logger.print(LogDesignColor.NORMAL + "Set maxX -> " + value);
	}

	@Override
	public List<String> onTabComplete(UserData user, String[] args) {
		if(args.length == 1) {
			return Arrays.asList(String.valueOf(user.getX()));
		}else {
			return new ArrayList<>();
		}
	}

}
