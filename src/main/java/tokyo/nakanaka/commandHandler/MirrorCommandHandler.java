package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.AdjustCommand;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.command.MirrorCommand;
import tokyo.nakanaka.command.UndoableCommand;
import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.logger.LogDesignColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class MirrorCommandHandler implements CommandHandler {
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
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.REQUIRED, new String[] {"x", "y", "z"}, ""));
		return list;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 1) {
			logger.print(LogDesignColor.ERROR + "Usage: " + "/sg "+ this.cmdHelp.getUsage());
			return false;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			logger.print(LogDesignColor.ERROR + "Can not parse axis");
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
		MirrorCommand mirrorCmd = new MirrorCommand(originalCmd, axis, player.getBlockPhysics());
		mirrorCmd.execute();
		undoManager.add(mirrorCmd);
		logger.print(LogDesignColor.NORMAL + "Mirrored along the " + axis.toString().toLowerCase() + " axis");
		return true;
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
