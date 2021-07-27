package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.BranchCommandHelpOld;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;

public class AxisCommandHandler implements SelSubCommandHandler{
	private BranchCommandHelpOld cmdHelp;
	
	public AxisCommandHandler() {
		this.cmdHelp = new BranchCommandHelpOld.Builder("axis")
				.description("Set axis")
				.addParameter(ParameterType.REQUIRED, new String[] {"x", "y", "z"})
				.build();
	}

	@Override
	public String getLabel() {
		return "axis";
	}
	
	@Override
	public String getDescription() {
		return "Set axis";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
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
