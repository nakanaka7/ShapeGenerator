package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.triangleSelSubCommandHandler;

import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TriangleRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;

/**
 * Handles "/sg sel thickness" command in triangle selection mode
 */
public class ThicknessCommandHandler extends LengthCommandHandler {

	public ThicknessCommandHandler() {
		super("thickness");
	}

	@Override
	protected RegionData newRegionData() {
		return new TriangleRegionData();
	}

	@Override
	protected void setParsedValue(RegionData regData, Double parsed) {
		var triRegData = (TriangleRegionData)regData;
		triRegData.setThickness(parsed);
	}
	
}
