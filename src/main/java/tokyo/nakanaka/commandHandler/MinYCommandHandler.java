package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.MinYCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.player.User;

public class MinYCommandHandler implements CommandHandler {
	
	@Override
	public String getLabel() {
		return "miny";
	}
	
	@Override
	public String getDescription() {
		return "Set min y of the generated blocks";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.REQUIRED, "value", "The y coordinate"));
		return list;
	}

	@Override
	public boolean onCommand(User user, String[] args) {
		Logger logger = user.getLogger();
		if(args.length != 1) {
			return false;
		}
		double value;
		try {
			value = Double.valueOf(args[0]);
		}catch(IllegalArgumentException e) {
			logger.print(LogDesignColor.ERROR + "Can not parse double");
			return true;
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
			return true;
		}
		MinYCommand minyCmd = new MinYCommand(originalCmd, value, user.getBlockPhysics());
		minyCmd.execute();
		undoManager.add(minyCmd);
		logger.print(LogDesignColor.NORMAL + "Set minY -> " + value);
		return true;
	}

	@Override
	public List<String> onTabComplete(User user, String[] args) {
		if(args.length == 1) {
			return Arrays.asList(String.valueOf(user.getY()));
		}else {
			return new ArrayList<>();
		}
	}

}
