package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.torusSelSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TorusRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.BaseSelSubCommandHandler;

/**
 * Handles "/sg sel axis" command in torus selection mode
 */
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
		return new TorusRegionData();
	}
	
	@Override
	protected void setParsedValue(RegionData regData, Axis parsed) {
		var torusRegData = (TorusRegionData)regData;
		torusRegData.setAxis(parsed);
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
		case 1 -> List.of("x", "y", "z");
		default -> List.of();
		};
	}
	
}
