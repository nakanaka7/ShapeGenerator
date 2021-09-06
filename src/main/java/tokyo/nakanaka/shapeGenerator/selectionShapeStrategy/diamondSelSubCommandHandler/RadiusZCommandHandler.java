package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.diamondSelSubCommandHandler;

import tokyo.nakanaka.shapeGenerator.regionData.DiamondRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;

/**
 * Handles "/sg sel radius_z" command in diamond selection mode
 */
public class RadiusZCommandHandler extends LengthCommandHandler {

	public RadiusZCommandHandler() {
		super("radius_z");
	}

	@Override
	protected RegionData newRegionData() {
		return new DiamondRegionData();
	}

	@Override
	protected void setParsedValue(RegionData regData, Double parsed) {
		var diamondRegData = (DiamondRegionData)regData;
		diamondRegData.setRadiusZ(parsed);
	}

}
