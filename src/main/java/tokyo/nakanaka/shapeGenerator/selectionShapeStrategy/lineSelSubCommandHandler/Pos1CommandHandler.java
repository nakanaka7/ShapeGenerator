package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.lineSelSubCommandHandler;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.LineRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.PosCommandHandler;

/**
 * Handles "/sg sel pos1" command in line selection mode
 */
public class Pos1CommandHandler extends PosCommandHandler {

	public Pos1CommandHandler() {
		super("pos1");
	}

	@Override
	protected RegionData newRegionData() {
		return new LineRegionData();
	}

	@Override
	protected void setParsedValue(RegionData regData, Vector3D parsed) {
		var lineRegData = (LineRegionData)regData;
		lineRegData.setPos1(parsed);
	}
	
}
