package tokyo.nakanaka.selection.selSubCommandHandler;


import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.BranchCommandHelpOld;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;

public class RegularPolygonSideCommandHandler implements SelSubCommandHandler {
	private BranchCommandHelpOld cmdHelp;
	
	public RegularPolygonSideCommandHandler() {
		this.cmdHelp = new BranchCommandHelpOld.Builder("side")
				.description("Set the side number, must be larger than or equal to 3")
				.addParameter(ParameterType.REQUIRED, "number")
				.build();
	}

	@Override
	public String getLabel() {
		return "side";
	}
	
	@Override
	public String getDescription() {
		return "Set the side number, must be larger than or equal to 3";
	}

	public BranchCommandHelpOld getCommandHelp() {
		return cmdHelp;
	}

	@Override
	public boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args) {
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: " + "/sg sel " + this.cmdHelp.getUsage());
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
	public List<String> onTabComplete(String[] args) {
		return Arrays.asList("3", "4", "5", "6", "7", "8", "9", "10");
	}
	
}
