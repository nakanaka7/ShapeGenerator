package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.Parameter;
import tokyo.nakanaka.commadHelp.Parameter.Type;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.ScaleCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class ScaleCommandHandler implements SgSubCommandHandler{
	private CommandHelp help = new CommandHelp.Builder("scale")
			.description("Change scale the generated blocks")
			.addParameter(new Parameter(Type.REQUIRED, "x", "y", "z"), "scale axis")
			.addParameter(new Parameter(Type.REQUIRED, "scale factor"), "scale factor (double data type)")
			.build();
	private String usage = "/sg scale <x|y|z> <factor>";
	
	@Override
	public CommandHelp getCommandHelp() {
		return this.help;
	}
	
	@Override
	public String getLabel() {
		return "scale";
	}
	
	@Override
	public String getDescription() {
		return "Change scale the generated blocks";
	}
	
	@Override
	public String getUsage() {
		return this.usage;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 2) {
			logger.print(LogColor.RED + "Usage: " + this.usage);
			return;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Can not parse axis");
			return;
		}
		double factor;
		try {
			factor = Double.valueOf(args[1]);
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Can not parse double");
			return;
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
		UndoableCommand cmd = undoManager.peekUndoCommand();
		GenerateCommand originalCmd;
		if(cmd instanceof GenerateCommand) {
			originalCmd = (GenerateCommand) cmd;
		}else if(cmd instanceof AdjustCommand) {
			originalCmd = ((AdjustCommand)cmd).getLastCommand();
		}else {
			logger.print(LogColor.RED + "Generate blocks first");
			return;
		}
		ScaleCommand scaleCmd = new ScaleCommand(originalCmd, axis, factor, player.getBlockPhysics());
		scaleCmd.execute();
		undoManager.add(scaleCmd);
		return;
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
