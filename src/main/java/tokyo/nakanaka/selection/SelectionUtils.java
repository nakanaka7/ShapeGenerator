package tokyo.nakanaka.selection;

import tokyo.nakanaka.commandArgument.CoordinateCommandArgument;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;

public class SelectionUtils {
	private SelectionUtils() {
	}
	
	public static Vector3D toVector3D(BlockVector3D offset, String[] args) {
		int offsetX = offset.getX();
		int offsetY = offset.getY();
		int offsetZ = offset.getZ();
		if(args.length == 0) {
			return new Vector3D(offsetX, offsetY, offsetZ);
		}else if(args.length == 3) {
			CoordinateCommandArgument coordArg = new CoordinateCommandArgument();
			double x = coordArg.onParsingDouble(args[0], offsetX);
			double y = coordArg.onParsingDouble(args[1], offsetY);
			double z = coordArg.onParsingDouble(args[2], offsetZ);
			return new Vector3D(x, y, z);
		}else {
			throw new IllegalArgumentException();
		}
	}
}
