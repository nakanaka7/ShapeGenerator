package tokyo.nakanaka.shapeGenerator.selSubCommandHandler;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.user.UserData;

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
	public boolean onCommand(UserData userData, Player player, String[] subArgs) {
		if(subArgs.length != 1) {
			player.print(LogColor.RED + "Usage: " + "/sg sel side [number]");
			return false;
		}
		Integer side = Integer.parseInt(subArgs[0]);
		if(side < 3) {
			player.print(LogColor.RED + "The number must be larger than or equal to 3");
			return false;
		}
		Map<String, Object> regDataMap = userData.getSelectionData().getRegionDataMap();
		regDataMap.put("side", side);
		return true;
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] subArgs) {
		return Arrays.asList("3", "4", "5", "6", "7", "8", "9", "10");
	}
	
}