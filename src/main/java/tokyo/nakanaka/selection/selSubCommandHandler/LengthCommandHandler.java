package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;

public class LengthCommandHandler implements SelSubCommandHandler {
	private String label;
	private String usage;
	
	public LengthCommandHandler(String label) {
		this.label = label;
		this.usage = "/sg sel " + label + " <length>";
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public String getDescription() {
		return "Set " + this.label + " (positive double value)";
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
		double value;
		try {
			value = Double.parseDouble(args[0]);
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Can not parse double");
			return true;
		}
		if(value <= 0) {
			logger.print(LogColor.RED + "The value must be larger than 0");
			return false;
		}
		data.putDouble(this.label, value);
		return true;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		return Arrays.asList("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0",
				"5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5", "10.0");
	}

}