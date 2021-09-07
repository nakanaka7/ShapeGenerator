package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.triangleSelSubCommandHandler;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TriangleRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.PosCommandHandler;

/**
 * Handles "/sg sel pos1" command in triangle selection mode
 */
public class Pos1CommandHandler extends PosCommandHandler {

	public Pos1CommandHandler() {
		super("pos1");
	}

	@Override
	protected RegionData newRegionData() {
		return new TriangleRegionData();
	}

	@Override
	protected void setParsedValue(RegionData regData, Vector3D parsed) {
		var triRegData = (TriangleRegionData)regData;
		triRegData.setPos1(parsed);
	}

}
