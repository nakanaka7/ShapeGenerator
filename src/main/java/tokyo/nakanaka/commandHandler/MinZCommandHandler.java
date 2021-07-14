package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.MinZCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class MinZCommandHandler implements BranchCommandHandler {
	private BranchCommandHelp cmdHelp = new BranchCommandHelp.Builder("minz")
			.description("Set min z of the generated blocks")
			.addParameter(ParameterType.REQUIRED, "value")
			.build();
	
	@Override
	public String getLabel() {
		return "minz";
	}
	
	@Override
	public String getDescription() {
		return "Set min z of the generated blocks";
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
		MinZCommand minzCmd = new MinZCommand(originalCmd, value, player.getBlockPhysics());
		minzCmd.execute();
		undoManager.add(minzCmd);
		logger.print(LogColor.DARK_AQUA + "Set minZ -> " + value);
		return;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList(String.valueOf(player.getZ()));
		}else {
			return new ArrayList<>();
		}
	}

}
