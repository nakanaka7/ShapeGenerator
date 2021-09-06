package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.lineSelSubCommandHandler;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.LineRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.PosCommandHandler;

/**
 * Handles "/sg sel pos2" command in line selection mode
 */
public class Pos2CommandHandler extends PosCommandHandler {

	public Pos2CommandHandler() {
		super("pos2");
	}

	@Override
	protected RegionData newRegionData() {
		return new LineRegionData();
	}

	@Override
	protected void setParsedValue(RegionData regData, Vector3D parsed) {
		var lineRegData = (LineRegionData)regData;
		lineRegData.setPos2(parsed);
	}
	
}
