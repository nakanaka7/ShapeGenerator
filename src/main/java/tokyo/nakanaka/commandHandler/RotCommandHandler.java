package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.RotateCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class RotCommandHandler implements BranchCommandHandler{
	private BranchCommandHelp cmdHelp;
	
	public RotCommandHandler() {
		this.cmdHelp = new BranchCommandHelp.Builder("rot")
				.description("Rotate the generated blocks")
				.addParameter(ParameterType.REQUIRED, new String[] {"x", "y", "z"})
				.addParameter(ParameterType.REQUIRED, "degree")
				.build();
	}
	
	@Override
	public String getLabel() {
		return "rot";
	}
	
	@Override
	public BranchCommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 2) {
			logger.print(LogColor.RED + "Usage: " + "/sg "+ this.cmdHelp.getUsage());
			return;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Can not parse axis");
			return;
		}
		double degree;
		try {
			degree = Double.valueOf(args[1]);
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Can not parse double");
			return;
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
		UndoableCommand cmd = undoManager.peekUndoCommand();
		if(cmd == null) {
			logger.print(LogColor.RED + "Generate blocks first");
			return;
		}
		GenerateCommand originalCmd;
		if(cmd instanceof GenerateCommand) {
			originalCmd = (GenerateCommand) cmd;
		}else if(cmd instanceof AdjustCommand) {
			originalCmd = ((AdjustCommand)cmd).getLastCommand();
		}else {
			logger.print(LogColor.RED + "Invalid Operation");
			return;
		}
		RotateCommand rotateCmd = new RotateCommand(originalCmd, axis, degree, player.getBlockPhysics());
		rotateCmd.execute();
		undoManager.add(rotateCmd);
		return;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("x", "y", "z");
		}else if(args.length == 2) {
			return Arrays.asList("0", "90", "-90", "180", "270");
		}else {
			return new ArrayList<>();
		}
	}

}
