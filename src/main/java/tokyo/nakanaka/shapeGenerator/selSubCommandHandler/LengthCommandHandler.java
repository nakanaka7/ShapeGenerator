package tokyo.nakanaka.shapeGenerator.selSubCommandHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.Utils;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class LengthCommandHandler implements SelSubCommandHandler {
	private String label;
	
	public LengthCommandHandler(String label) {
		this.label = label;
	}

	@Override
	public void onCommand(UserData userData, Player player, String[] subArgs) {
		if(subArgs.length != 1) {
			player.print(LogColor.RED + "Usage: " + "/sg sel " + this.label + "<length>");
			return;
		}
		double value;
		try {
			value = Double.parseDouble(subArgs[0]);
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Can not parse double");
			return;
		}
		if(value <= 0) {
			player.print(LogColor.RED + "The value must be larger than 0");
			return;
		}
		Map<String, Object> regDataMap = userData.getSelectionData().getRegionDataMap();
		regDataMap.put(this.label, value);
		List<String> lines = Utils.getSelectionMessageLines(userData.getSelectionData());
		for(String line : lines) {
			player.print(line);
		}
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] subArgs) {
		return Arrays.asList("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0",
				"5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5", "10.0");
	}

}
