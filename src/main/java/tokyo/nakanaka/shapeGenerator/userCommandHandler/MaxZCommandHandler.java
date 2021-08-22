package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.MaxZCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;
import tokyo.nakanaka.shapeGenerator.commandHelp.MaxzHelp;
import tokyo.nakanaka.shapeGenerator.user.UserOld;

public class MaxZCommandHandler implements UserCommandHandler {	
	@Override
	public String getLabel() {
		return "maxz";
	}

	@Override
	public String getDescription() {
		return "Set max z of the generated blocks";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.REQUIRED, "value", "The z coordinate"));
		return list;
	}
	
	@Override
	public void onCommand(UserOld user, String[] args) {
		Logger logger = user.getLogger();
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: " + new MaxzHelp().getUsage());
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
		MaxZCommand maxzCmd = new MaxZCommand(originalCmd, value, user.getBlockPhysics());
		maxzCmd.execute();
		undoManager.add(maxzCmd);
		logger.print(LogDesignColor.NORMAL + "Set maxY -> " + value);
	}

	@Override
	public List<String> onTabComplete(UserOld user, String[] args) {
		if(args.length == 1) {
			return Arrays.asList(String.valueOf(user.getZ()));
		}else {
			return new ArrayList<>();
		}
	}

}
