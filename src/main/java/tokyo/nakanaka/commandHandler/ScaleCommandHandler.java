package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.ScaleCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.logger.LogDesignColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class ScaleCommandHandler implements CommandHandler{

	@Override
	public String getLabel() {
		return "scale";
	}
	
	@Override
	public String getDescription() {
		return "Change scale of the generated blocks";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.REQUIRED, new String[] {"x", "y", "z"}, ""));
		list.add(new ParameterHelp(ParameterType.REQUIRED, "factor", ""));
		return list;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 2) {
			return false;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			logger.print(LogDesignColor.ERROR + "Can not parse axis");
			return true;
		}
		double factor;
		try {
			factor = Double.valueOf(args[1]);
		}catch(IllegalArgumentException e) {
			logger.print(LogDesignColor.ERROR + "Can not parse double");
			return true;
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
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
		ScaleCommand scaleCmd = new ScaleCommand(originalCmd, axis, factor, player.getBlockPhysics());
		scaleCmd.execute();
		undoManager.add(scaleCmd);
		logger.print(LogDesignColor.NORMAL + "Scaled " + factor + " times along the " + axis.toString().toLowerCase() + " axis");
		return true;
	}
	
	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("x", "y", "z");
		}else if(args.length == 2) {
			return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}else {
			return new ArrayList<>();
		}
	}

}
