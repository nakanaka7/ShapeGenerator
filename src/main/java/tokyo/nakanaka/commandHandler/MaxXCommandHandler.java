package tokyo.nakanaka.commandHandler;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.MaxXCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class MaxXCommandHandler implements CommandHandler {
	private BranchCommandHelp cmdHelp = new BranchCommandHelp.Builder("maxx")
			.addParameter(ParameterType.REQUIRED, "value")
			.build();
	
	@Override
	public String getLabel() {
		return "maxx";
	}

	@Override
	public CommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}

	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: " + "/sg "+ this.cmdHelp.getUsage());
			return;
		}
		double value;
		try {
			value = Double.valueOf(args[0]);
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Can not parse double");
			return;
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
			logger.print(LogColor.RED + "Generate blocks first");
			return;
		}
		MaxXCommand maxxCmd = new MaxXCommand(originalCmd, value, player.getBlockPhysics());
		maxxCmd.execute();
		undoManager.add(maxxCmd);
		logger.print(LogColor.DARK_AQUA + "Set maxX -> " + value);
		return;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		return Arrays.asList("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0",
				"5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5", "10.0");
	}
	
}
