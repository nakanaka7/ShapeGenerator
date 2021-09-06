package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.lineSelSubCommandHandler;

import tokyo.nakanaka.shapeGenerator.regionData.LineRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;

/**
 * Handles "/sg sel thickness" command in line selection mode
 */
public class ThicknessCommandHandler extends LengthCommandHandler {

	public ThicknessCommandHandler() {
		super("thickness");
	}

	@Override
	protected RegionData newRegionData() {
		return new LineRegionData();
	}

	@Override
	protected void setParsedValue(RegionData regData, Double parsed) {
		var lineRegData = (LineRegionData)regData;
		lineRegData.setThickness(parsed);
	}
	
}
