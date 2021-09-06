package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.regularPolygonSelSubCommandHandler;

import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegularPolygonRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;

/**
 * Handles "/sg sel radius" command in regular polygon selection mode
 */
public class RadiusCommandHandler extends LengthCommandHandler {

	public RadiusCommandHandler() {
		super("radius");
	}

	@Override
	protected RegionData newRegionData() {
		return new RegularPolygonRegionData();
	}

	@Override
	protected void setLength(RegionData regData, Double length) {
		var rpRegData = (RegularPolygonRegionData)regData;
		rpRegData.setRadius(length);
	}

}
