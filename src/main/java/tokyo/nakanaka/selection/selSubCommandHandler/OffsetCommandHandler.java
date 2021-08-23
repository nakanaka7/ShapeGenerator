package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.commandArgument.CoordinateCommandArgument;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.shapeGenerator.user.UserData;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.UserCommandHandler;

public class OffsetCommandHandler implements UserCommandHandler {
	private SelectionMessenger selMessenger = new SelectionMessenger();
	
	@Override
	public String getLabel() {
		return "offset";
	}
	
	@Override
	public String getDescription() {
		return "Set offset";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.OPTIONAL, "x", ""));
		list.add(new ParameterHelp(ParameterType.OPTIONAL, "y", ""));
		list.add(new ParameterHelp(ParameterType.OPTIONAL, "z", ""));
		return list;
	}

	public void onCommand(UserData player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 0 && args.length != 3) {
			logger.print(LogColor.RED + "Usage: " + "/sg sel offset [x] [y] [z]");
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
		logger.print(LogColor.RED + "Usage: " + "/sg sel offset [x] [y] [z]");
		logger.print(LogColor.RED + "Note: When specifing the coordinates, [x], [y], [z] must be given altogether");
	}
	
	public List<String> onTabComplete(UserData user, String[] args) {
		if(args.length <= 3) {
			return Arrays.asList("~");
		}else {
			return new ArrayList<>();
		}
	}

}
