package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.torusSelSubCommandHandler;

import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TorusRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;

/**
 * Handles "/sg sel torus_sub" command in torus selection mode
 */
public class RadiusSubCommandHandler extends LengthCommandHandler {

	public RadiusSubCommandHandler() {
		super("radius_sub");
	}

	@Override
	protected RegionData newRegionData() {
		return new TorusRegionData();
	}

	@Override
	protected void setParsedValue(RegionData regData, Double parsed) {
		var torusRegData = (TorusRegionData)regData;
		torusRegData.setRadiusSub(parsed);
	}
	
}
