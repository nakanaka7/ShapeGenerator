package tokyo.nakanaka.commandArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;

public class PositionArgumentList{

	public Vector3D onParse(BlockVector3D offset, String[] args) {
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

	public List<String> onTabComplete(String[] args) {
		if(args.length <= 3) {
			return Arrays.asList("~");
		}else {
			return new ArrayList<>();
		}
	}

}
