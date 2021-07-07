package tokyo.nakanaka.selection;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;

public class AxisCommandHandler implements SelSubCommandHandler{
	private String usage = "/sg sel axis <x|y|z>";
	
	@Override
	public String getLabel() {
		return "axis";
	}
	
	@Override
	public String getDescription() {
		return "Set axis";
	}

	@Override
	public String getUsage() {
		return this.usage;
	}
	
	@Override
	public boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args) {
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: " + this.usage);
			return false;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Invalid axis");
			return false;
		}
		data.putString("axis", axis.toString().toLowerCase());
		return true;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		return Arrays.asList("x", "y", "z");
	}

}
