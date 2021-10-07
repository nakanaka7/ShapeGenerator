package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.command.ScaleCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgSubcommandHelps;

/**
 * Handles "/sg scale" command
 */
public class ScaleCommandHandler implements SubCommandHandler {

	@Override
	public String label() {
		return "scale";
	}

	@Override
	public String description() {
		return "Change the scale of the generated block(s)";
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
		String usage = Main.SG + " " + SgSublabel.SCALE + " " + String.join(" ", SgSubcommandHelps.SCALE.parameterSyntaxes());
		if(args.length != 2) {
			player.print(cmdLogColor.error() + "Usage: " + usage);
			return;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(cmdLogColor.error() + "Can not parse axis");
			return;
		}
		double factor;
		try {
			factor = Double.valueOf(args[1]);
		}catch(IllegalArgumentException e) {
			player.print(cmdLogColor.error() + "Can not parse double");
			return;
		}
		if(factor == 0) {
			player.print(cmdLogColor.error() + "The scale factor must not be zero");
			return;
		}
		UndoCommandManager undoManager = playerData.getUndoCommandManager();
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
			player.print(cmdLogColor.error() + "Generate blocks first");
			return;
		}
		ScaleCommand scaleCmd = new ScaleCommand(originalCmd, axis, factor, playerData.getBlockPhysics());
		scaleCmd.execute();
		undoManager.add(scaleCmd);
		player.print(cmdLogColor.main() + "Scaled " + factor + " times along the " + axis.toString().toLowerCase() + " axis");
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> List.of("x", "y", "z");
			case 2 -> List.of("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0",
					"5.0", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5");
			default -> List.of();
		};
	}

}
