package tokyo.nakanaka.shapeGenerator.selSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandArgument.CoordinateCommandArgument;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.Utils;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class OffsetCommandHandler implements SubCommandHandler {
	
	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		SelectionData selData = userData.getSelectionData();
		if(args.length != 0 && args.length != 3) {
			player.print(LogColor.RED + "Usage: " + "/sg sel offset [x] [y] [z]");
			player.print(LogColor.RED + "Note: When specifing the coordinates, [x], [y], [z] must be given altogether");
			return;
		}
		Vector3D pos;
		if(args.length == 0) {
			BlockPosition playerPos = player.getBlockPosition();
			pos = new Vector3D(playerPos.x(), playerPos.y(), playerPos.z());
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
				player.print(LogColor.RED + "Can not parse the coordinates");
				return;
			}
			pos = new Vector3D(x, y, z);
		}else {
			return;
		}
		selData.setOffset(pos);
		List<String> lines = Utils.getSelectionMessageLines(selData);
		for(String line : lines) {
			player.print(line);
		}
	}
	
	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		BlockPosition pos = player.getBlockPosition();
		return switch(args.length) {
			case 1 -> List.of(String.valueOf(pos.x()));
			case 2 -> List.of(String.valueOf(pos.y()));
			case 3 -> List.of(String.valueOf(pos.z()));
			default -> List.of();
		};
	}

}
