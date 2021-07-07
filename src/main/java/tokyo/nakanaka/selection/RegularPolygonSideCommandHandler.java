package tokyo.nakanaka.selection;


import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;

public class RegularPolygonSideCommandHandler implements SelSubCommandHandler {
	private String usage = "/sg sel side <num>";
	
	@Override
	public String getLabel() {
		return "side";
	}

	@Override
	public String getDescription() {
		return "Set the side numbers, must be larger than 3 (inclusive)";
	}
	
	@Override
	public String getUsage() {
		return this.usage;
	}
	
	@Override
	public boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args) {
		if(args.length != 1) {
			throw new IllegalArgumentException();
		}
		Integer side = Integer.parseInt(args[0]);
		if(side < 3) {
			logger.print(LogColor.RED + "Must be 3, 4, 5...");
			return true;
		}
		data.putInteger("side", side);
		return true;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		return Arrays.asList("3", "4", "5", "6", "7", "8", "9", "10");
	}
	
}
