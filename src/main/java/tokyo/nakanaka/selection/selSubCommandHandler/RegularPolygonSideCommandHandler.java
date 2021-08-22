package tokyo.nakanaka.selection.selSubCommandHandler;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.shapeGenerator.user.UserOld;

public class RegularPolygonSideCommandHandler implements SelSubCommandHandler {
	
	@Override
	public String getLabel() {
		return "side";
	}
	
	@Override
	public String getDescription() {
		return "Set the side number, must be larger than or equal to 3";
	}

	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.REQUIRED, "number", ""));
		return new ArrayList<>();
	}
	
	@Override
	public boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args) {
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: " + "/sg sel side [number]");
			return false;
		}
		Integer side = Integer.parseInt(args[0]);
		if(side < 3) {
			logger.print(LogColor.RED + "The number must be larger than or equal to 3");
			return false;
		}
		data.putInteger("side", side);
		return true;
	}

	@Override
	public List<String> onTabComplete(UserOld user, String[] args) {
		return Arrays.asList("3", "4", "5", "6", "7", "8", "9", "10");
	}
	
}
