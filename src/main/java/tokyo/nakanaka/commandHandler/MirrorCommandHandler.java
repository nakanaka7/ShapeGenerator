package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.MirrorCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class MirrorCommandHandler implements BranchCommandHandler {
	private BranchCommandHelp cmdHelp;
	
	public MirrorCommandHandler() {
		this.cmdHelp = new BranchCommandHelp.Builder("mirror")
				.description("Mirror the generated blocks")
				.addParameter(ParameterType.REQUIRED, new String[] {"x", "y", "z"})
				.build();
	}
	
	@Override
	public String getLabel() {
		return "mirror";
	}
	
	@Override
	public String getDescription() {
		return "Mirror the generated blocks";
	}
	
	@Override
	public BranchCommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 1) {
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
		MirrorCommand mirrorCmd = new MirrorCommand(originalCmd, axis, player.getBlockPhysics());
		mirrorCmd.execute();
		undoManager.add(mirrorCmd);
		logger.print(LogColor.DARK_AQUA + "Mirrored along the " + axis.toString().toLowerCase() + " axis");
		return;
	}
	
	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("x", "y", "z");
		}else {
			return new ArrayList<>();
		}
	}
	
}
