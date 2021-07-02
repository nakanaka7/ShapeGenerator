package tokyo.nakanaka.selection;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;

public class AxisCommandHandler implements SelSubCommandHandler{

	@Override
	public String getLabel() {
		return "axis";
	}

	@Override
	public boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args) {
		if(args.length != 1) {
			return false;
		}
		try{
			Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Invalid axis");
		}
		data.putString("axis", args[0]);
		return true;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		return Arrays.asList("x", "y", "z");
	}

}
