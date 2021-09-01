package tokyo.nakanaka.shapeGenerator.selSubCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandArgument.CoordinateCommandArgument;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.SgSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class OffsetCommandHandler implements SgSubCommandHandler {
	private SelectionMessenger selMessenger = new SelectionMessenger();
	
	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		Logger logger = userData.getLogger();
		if(args.length != 0 && args.length != 3) {
			logger.print(LogColor.RED + "Usage: " + "/sg sel offset [x] [y] [z]");
			logger.print(LogColor.RED + "Note: When specifing the coordinates, [x], [y], [z] must be given altogether");
			return;
		}
		Vector3D pos;
		if(args.length == 0) {
			pos = new Vector3D(userData.getX(), userData.getY(), userData.getZ());
		}else if(args.length == 3) {
			CoordinateCommandArgument coordArg = new CoordinateCommandArgument();
			double x;
			double y;
			double z;
			try {
				x = coordArg.onParsingDouble(args[0], userData.getX());
				y = coordArg.onParsingDouble(args[1], userData.getY());
				z = coordArg.onParsingDouble(args[2], userData.getZ());
			}catch(IllegalArgumentException e) {
				logger.print(LogColor.RED + "Can not parse the coordinates");
				return;
			}
			pos = new Vector3D(x, y, z);
		}else {
			return;
		}
		SelectionBuildingData selData = userData.getSelectionBuildingData();
		selData.setOffset(pos);
		this.selMessenger.printSelection(logger, userData.getSelectionShapeNew(), selData, "");
		logger.print(LogColor.RED + "Usage: " + "/sg sel offset [x] [y] [z]");
		logger.print(LogColor.RED + "Note: When specifing the coordinates, [x], [y], [z] must be given altogether");
	}
	
	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		if(args.length <= 3) {
			return Arrays.asList("~");
		}else {
			return new ArrayList<>();
		}
	}

}
