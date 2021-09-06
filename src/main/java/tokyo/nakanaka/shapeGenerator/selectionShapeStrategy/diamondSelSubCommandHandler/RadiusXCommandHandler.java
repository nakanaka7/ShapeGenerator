package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.diamondSelSubCommandHandler;

import tokyo.nakanaka.shapeGenerator.regionData.DiamondRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;

/**
 * Handles "/sg sel radius_x" command in diamond selection mode
 */
public class RadiusXCommandHandler extends LengthCommandHandler {

	public RadiusXCommandHandler() {
		super("radius_x");
	}

	@Override
	protected RegionData newRegionData() {
		return new DiamondRegionData();
	}

	@Override
	protected void setLength(RegionData regData, Double length) {
		var diamondRegData = (DiamondRegionData)regData;
		diamondRegData.setRadiusX(length);
	}
	
}
