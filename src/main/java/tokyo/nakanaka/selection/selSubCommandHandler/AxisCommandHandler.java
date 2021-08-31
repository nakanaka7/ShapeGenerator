package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.selection.RegionBuildingData;
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
	public boolean onCommand(RegionBuildingData data, Player player, String[] args) {
		if(args.length != 1) {
			player.print(LogColor.RED + "Usage: " + "/sg sel axis <x|y|z>");
			return false;
		}
		Axis axis;
		try{
			axis = Axis.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Invalid axis");
			return false;
		}
		data.putString("axis", axis.toString().toLowerCase());
		return true;
	}

	@Override
	public List<String> onTabComplete(UserData user, Player player, String[] subArgs) {
		return Arrays.asList("x", "y", "z");
	}

}
