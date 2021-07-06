package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commandArgument.CoordinateCommandArgument;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;

public class PosCommandHandler implements SelSubCommandHandler {
	private String label;
	private String usage;
	
	public PosCommandHandler(String label) {
		this.label = label;
		this.usage = "/sg sel " + label + "[x] [y] [z]";
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args) {
		if(args.length != 0 && args.length != 3) {
			logger.print(LogColor.RED + "Usage: " + this.usage);
			logger.print(LogColor.RED + "Note: When specifing the coordinates, [x], [y], [z] must be given altogether");
			return false;
		}
		Vector3D pos;
		if(args.length == 0) {
			pos = playerPos.toVector3D();
		}else if(args.length == 3) {
			CoordinateCommandArgument coordArg = new CoordinateCommandArgument();
			double x = coordArg.onParsingDouble(args[0], playerPos.getX());
			double y = coordArg.onParsingDouble(args[1], playerPos.getY());
			double z = coordArg.onParsingDouble(args[2], playerPos.getZ());
			pos = new Vector3D(x, y, z);
		}else {
			logger.print(LogColor.RED + "Can not parse the coordinates");
			return false;
		}
		data.putVector3D(this.label, pos);
		return true;
	}
	
	@Override
	public List<String> onTabComplete(String[] args) {
		if(args.length <= 3) {
			return Arrays.asList("~");
		}else {
			return new ArrayList<>();
		}
	}
	
}
