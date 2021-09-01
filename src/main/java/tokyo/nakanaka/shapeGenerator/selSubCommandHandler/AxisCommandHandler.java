package tokyo.nakanaka.shapeGenerator.selSubCommandHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.Utils;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class AxisCommandHandler implements SelSubCommandHandler{

	@Override
	public void onCommand(UserData userData, Player player, String[] subArgs) {
		if(subArgs.length != 1) {
			player.print(LogColor.RED + "Usage: " + "/sg sel axis <x|y|z>");
			return;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(subArgs[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Invalid axis");
			return;
		}
		Map<String, Object> regDataMap = userData.getSelectionData().getRegionDataMap();
		regDataMap.put("axis", axis.toString().toLowerCase());
		List<String> lines = Utils.getSelectionMessageLines(userData.getSelectionData());
		for(String line : lines) {
			player.print(line);
		}
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] subArgs) {
		return Arrays.asList("x", "y", "z");
	}

}
