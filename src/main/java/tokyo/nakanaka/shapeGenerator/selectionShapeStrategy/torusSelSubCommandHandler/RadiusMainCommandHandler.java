package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.torusSelSubCommandHandler;

import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TorusRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;

/**
 * Handles "/sg sel radius_main" command in torus selection mode
 */
public class RadiusMainCommandHandler extends LengthCommandHandler {

	public RadiusMainCommandHandler() {
		super("radius_main");
	}

	@Override
	protected RegionData newRegionData() {
		return new TorusRegionData();
	}

	@Override
	protected void setParsedValue(RegionData regData, Double parsed) {
		var torusRegData = (TorusRegionData)regData;
		torusRegData.setRadiusMain(parsed);
	}

}
