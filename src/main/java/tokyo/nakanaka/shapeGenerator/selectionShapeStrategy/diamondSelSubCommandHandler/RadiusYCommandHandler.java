package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.diamondSelSubCommandHandler;

import tokyo.nakanaka.shapeGenerator.regionData.DiamondRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;

/**
 * Handles "/sg sel radius_y" command in diamond selection mode
 */
public class RadiusYCommandHandler extends LengthCommandHandler {

	public RadiusYCommandHandler() {
		super("radius_y");
	}

	@Override
	protected RegionData newRegionData() {
		return new DiamondRegionData();
	}

	@Override
	protected void setLength(RegionData regData, Double length) {
		var diamondRegData = (DiamondRegionData)regData;
		diamondRegData.setRadiusY(length);
	}

}
