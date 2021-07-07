package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commandArgument.CoordinateCommandArgument;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;

public class OffsetCommandHandler {
	private CommandHelp cmdHelp;
	private SelectionMessenger selMessenger = new SelectionMessenger();
	
	public OffsetCommandHandler() {
		String desc = "Set offset";
		String usage = "/offset [x] [y] [z]";
		this.cmdHelp = new CommandHelp(desc, usage);
	}
	
	public CommandHelp getCommandHelp() {
		return cmdHelp;
	}

	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 0 && args.length != 3) {
			logger.print(LogColor.RED + "Usage: " + this.cmdHelp.getUsage());
			logger.print(LogColor.RED + "Note: When specifing the coordinates, [x], [y], [z] must be given altogether");
			return;
		}
		Vector3D pos;
		if(args.length == 0) {
			pos = new Vector3D(player.getX(), player.getY(), player.getZ());
		}else if(args.length == 3) {
			CoordinateCommandArgument coordArg = new CoordinateCommandArgument();
			double x;
			double y;
			double z;
			try {
				x = coordArg.onParsingDouble(args[0], player.getX());
				y = coordArg.onParsingDouble(args[1], player.getY());
				z = coordArg.onParsingDouble(args[2], player.getZ());
			}catch(IllegalArgumentException e) {
				logger.print(LogColor.RED + "Can not parse the coordinates");
				return;
			}
			pos = new Vector3D(x, y, z);
		}else {
			return;
		}
		SelectionBuildingData selData = player.getSelectionBuildingData();
		selData.setOffset(pos);
		this.selMessenger.sendMessage(logger, player.getSelectionShape(), selData, "");
	}
	
	public List<String> onTabComplete(String[] args) {
		return new ArrayList<>();
	}
}
