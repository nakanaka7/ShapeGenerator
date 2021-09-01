package tokyo.nakanaka.shapeGenerator.selSubCommandHandler;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.Utils;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class RegularPolygonSideCommandHandler implements SelSubCommandHandler {
	
	@Override
	public void onCommand(UserData userData, Player player, String[] subArgs) {
		if(subArgs.length != 1) {
			player.print(LogColor.RED + "Usage: " + "/sg sel side [number]");
			return;
		}
		Integer side = Integer.parseInt(subArgs[0]);
		if(side < 3) {
			player.print(LogColor.RED + "The number must be larger than or equal to 3");
			return;
		}
		Map<String, Object> regDataMap = userData.getSelectionData().getRegionDataMap();
		regDataMap.put("side", side);
		List<String> lines = Utils.getSelectionMessageLines(userData.getSelectionData());
		for(String line : lines) {
			player.print(line);
		}
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] subArgs) {
		return Arrays.asList("3", "4", "5", "6", "7", "8", "9", "10");
	}
	
}
