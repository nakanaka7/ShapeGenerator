package tokyo.nakanaka.commandHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.ShiftCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.geometricProperty.Direction;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.player.Player;

public class ShiftCommandHandler implements CommandHandler{
	private BranchCommandHelp cmdHelp;
		
	public ShiftCommandHandler() {
		this.cmdHelp = new BranchCommandHelp.Builder("shift")
				.description("Shift the generated blocks")
				.addParameter(ParameterType.REQUIRED, "direction")
				.addParameter(ParameterType.REQUIRED, "length")
				.build();
	}

	@Override
	public String getLabel() {
		return "shift";
	}
	
	@Override
	public BranchCommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 2) {
			logger.print(LogColor.RED + "Usage: " + "/sg " + this.cmdHelp.getUsage());
			return;
		}
		Direction dir;
		double blocks;
		try {
			dir = Direction.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Can not parse direction");
			return;
		}
		try {
			blocks = Double.parseDouble(args[1]);
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Can not parse integer");
			return;
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
		UndoableCommand cmd = undoManager.getLastUndoCommand();
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
		double dx = dir.getX() * blocks;
		double dy = dir.getY() * blocks;
		double dz = dir.getZ() * blocks;
		Vector3D displacement = new Vector3D(dx, dy, dz);
		ShiftCommand shiftCmd = new ShiftCommand(originalCmd, displacement, player.getBlockPhysics());
		shiftCmd.execute();
		undoManager.add(shiftCmd);
		logger.print(LogColor.DARK_AQUA + "Shifted block(s) " + blocks + " " + dir.toString().toLowerCase());
		return;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList(Direction.values()).stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
		}else if(args.length == 2) {
			return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}else {
			return Arrays.asList();
		}
	}

}
