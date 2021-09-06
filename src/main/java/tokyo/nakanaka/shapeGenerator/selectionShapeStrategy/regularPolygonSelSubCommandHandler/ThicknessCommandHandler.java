package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.regularPolygonSelSubCommandHandler;

import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegularPolygonRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;

/**
 * Handles "/sg sel thickness" command in regular polygon selection mode
 */
public class ThicknessCommandHandler extends LengthCommandHandler {

	public ThicknessCommandHandler() {
		super("thickness");
	}

	@Override
	protected RegionData newRegionData() {
		return new RegularPolygonRegionData();
	}

	@Override
	protected void setLength(RegionData regData, Double length) {
		var rpRegData = (RegularPolygonRegionData)regData;
		rpRegData.setThickness(length);
	}
	
}
