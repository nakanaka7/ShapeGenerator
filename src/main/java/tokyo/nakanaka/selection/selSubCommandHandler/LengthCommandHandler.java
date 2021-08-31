package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class LengthCommandHandler implements SelSubCommandHandler {
	private String label;
	
	public LengthCommandHandler(String label) {
		this.label = label;
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
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.REQUIRED, "length", ""));
		return new ArrayList<>();
	}
	
	@Override
	public boolean onCommand(RegionBuildingData data, Player player, String[] subArgs) {
		if(subArgs.length != 1) {
			player.print(LogColor.RED + "Usage: " + "/sg sel " + this.label + "<length>");
			return false;
		}
		double value;
		try {
			value = Double.parseDouble(subArgs[0]);
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Can not parse double");
			return true;
		}
		if(value <= 0) {
			player.print(LogColor.RED + "The value must be larger than 0");
			return false;
		}
		data.putDouble(this.label, value);
		return true;
	}

	@Override
	public List<String> onTabComplete(UserData user, Player player, String[] subArgs) {
		return Arrays.asList("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0",
				"5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5", "10.0");
	}

}
