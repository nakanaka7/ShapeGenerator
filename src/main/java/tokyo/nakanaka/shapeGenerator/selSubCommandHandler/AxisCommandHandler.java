package tokyo.nakanaka.shapeGenerator.selSubCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.Utils;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class AxisCommandHandler implements SelSubCommandHandler{

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
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.REQUIRED, new String[] {"x", "y", "z"}, ""));
		return list;
	}
	
	@Override
	public boolean onCommand(UserData userData, Player player, String[] subArgs) {
		if(subArgs.length != 1) {
			player.print(LogColor.RED + "Usage: " + "/sg sel axis <x|y|z>");
			return false;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(subArgs[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Invalid axis");
			return false;
		}
		Map<String, Object> regDataMap = userData.getSelectionData().getRegionDataMap();
		regDataMap.put("axis", axis.toString().toLowerCase());
		List<String> lines = Utils.getSelectionMessageLines(userData.getSelectionData());
		for(String line : lines) {
			player.print(line);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] subArgs) {
		return Arrays.asList("x", "y", "z");
	}

}
