package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.commandArgument.CoordinateCommandArgument;
import tokyo.nakanaka.commandHandler.CommandHandler;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;

public class OffsetCommandHandler implements CommandHandler {
	private BranchCommandHelp cmdHelp;
	private SelectionMessenger selMessenger = new SelectionMessenger();
	
	public OffsetCommandHandler() {
		this.cmdHelp = new BranchCommandHelp.Builder("offset")
				.description("Set offset")
				.addParameter(ParameterType.OPTIONAL, "x")
				.addParameter(ParameterType.OPTIONAL, "y")
				.addParameter(ParameterType.OPTIONAL, "z")
				.build();
	}
	
	@Override
	public String getLabel() {
		return "offset";
	}

	@Override
	public CommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}
	
	public BranchCommandHelp getCommandHelp() {
		return cmdHelp;
	}

	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 0 && args.length != 3) {
			logger.print(LogColor.RED + "Usage: " + "/sg sel " + this.cmdHelp.getUsage());
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
		this.selMessenger.printSelection(logger, player.getSelectionShape(), selData, "");
	}
	
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length <= 3) {
			return Arrays.asList("~");
		}else {
			return new ArrayList<>();
		}
	}

}
