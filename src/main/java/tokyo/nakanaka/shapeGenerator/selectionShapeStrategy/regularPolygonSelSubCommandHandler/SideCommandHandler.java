package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.regularPolygonSelSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegularPolygonRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.BaseSelSubCommandHandler;

/**
 * Handles "/sg sel side" command in regular polygon selection mode
 */
public class SideCommandHandler extends BaseSelSubCommandHandler<Integer> {

	public SideCommandHandler() {
		super("/sg sel side <number>");
	}

	@Override
	protected Integer parse(Player player, String[] args) {
		if(args.length != 1) {
			throw new IllegalArgumentException();
		}
		int value = Integer.parseInt(args[0]);
		if(value < 3) {
			throw new IllegalArgumentException();
		}
		return value;
	}

	@Override
	protected RegionData newRegionData() {
		return new RegularPolygonRegionData();
	}

	@Override
	protected void setParsedValue(RegionData regData, Integer parsed) {
		var rpRegionData = (RegularPolygonRegionData)regData;
		rpRegionData.setSide(parsed);
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
		case 1 -> List.of("3", "4", "5", "6", "7", "8", "9");
		default -> List.of();
		};
	}

}
