package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.regularPolygonSelSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegularPolygonRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.BaseSelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class AxisCommandHandler extends BaseSelSubCommandHandler<Axis> {

	public AxisCommandHandler() {
		super("/sg sel axis <x|y|z>");
	}

	@Override
	protected Axis parse(Player player, String[] args) {
		return switch(args.length) {
		case 1 -> Axis.valueOf(args[0].toUpperCase());
		default -> throw new IllegalArgumentException();
		};
	}

	@Override
	protected RegionData newRegionData() {
		return new RegularPolygonRegionData();
	}
	
	@Override
	protected void setParsedValue(RegionData regData, Axis parsed) {
		var rpRegData = (RegularPolygonRegionData)regData;
		rpRegData.setAxis(parsed);
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		return switch(args.length) {
		case 1 -> List.of("x", "y", "z");
		default -> List.of();
		};
	}
	
}
