package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.commandArgument.CoordinateCommandArgument;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class PosCommandHandler implements SelSubCommandHandler {
	private String label;
	
	public PosCommandHandler(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public String getDescription() {
		return "Set position of " + this.label;
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.OPTIONAL, "x", ""));
		list.add(new ParameterHelp(ParameterType.OPTIONAL, "y", ""));
		list.add(new ParameterHelp(ParameterType.OPTIONAL, "z", ""));
		return list;
	}
	
	@Override
	public boolean onCommand(RegionBuildingData data, Player player, String[] args) {
		if(args.length != 0 && args.length != 3) {
			player.print(LogColor.RED + "Usage: " + "/sg sel " + this.label + "[x] [y] [z]");
			player.print(LogColor.RED + "Note: When specifing the coordinates, [x], [y], [z] must be given altogether");
			return false;
		}
		Vector3D pos;
		BlockPosition playerPos = player.getBlockPosition();
		if(args.length == 0) {
			pos = new Vector3D(playerPos.x(), playerPos.y(), playerPos.z());
		}else if(args.length == 3) {
			CoordinateCommandArgument coordArg = new CoordinateCommandArgument();
			double x;
			double y;
			double z;
			try {
				x = coordArg.onParsingDouble(args[0], playerPos.x());
				y = coordArg.onParsingDouble(args[1], playerPos.y());
				z = coordArg.onParsingDouble(args[2], playerPos.z());
			}catch(IllegalArgumentException e) {
				player.print(LogColor.RED + "Can not parse the coordinates");
				return false;
			}
			pos = new Vector3D(x, y, z);
		}else {
			return false;
		}
		data.putVector3D(this.label, pos);
		return true;
	}
	
	@Override
	public List<String> onTabComplete(UserData user, Player player, String[] subArgs) {
		if(subArgs.length <= 3) {
			return Arrays.asList("~");
		}else {
			return new ArrayList<>();
		}
	}

}
