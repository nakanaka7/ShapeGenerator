package tokyo.nakanaka.selection.sphere;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.logger.LogConstant;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelSubCommandHandler;

public class RadiusCommandHandler implements SelSubCommandHandler{
	
	@Override
	public String getLabel() {
		return "radius";
	}

	@Override
	public boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args) {
		if(args.length != 1) {
			return false;
		}
		double radius;
		try {
			radius = Double.parseDouble(args[0]);
		}catch(IllegalArgumentException e) {
			logger.print(LogConstant.HEAD_ERROR + "Can not parse double");
			return true;
		}
		if(radius < 0) {
			logger.print(LogConstant.HEAD_ERROR + "Radius must be larger than 0");
			return true;
		}
		data.putDouble("radius", radius);
		return true;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		return Arrays.asList("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0",
				"5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5", "10.0");
	}

}
