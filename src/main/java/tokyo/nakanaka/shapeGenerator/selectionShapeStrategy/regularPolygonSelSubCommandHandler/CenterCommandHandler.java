package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.regularPolygonSelSubCommandHandler;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegularPolygonRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.PosCommandHandler;

/**
 * Handles "/sg sel center" command in regular polygon selection mode
 */
public class CenterCommandHandler extends PosCommandHandler {

	public CenterCommandHandler() {
		super("center");
	}

	@Override
	protected RegionData newRegionData() {
		return new RegularPolygonRegionData();
	}

	@Override
	protected void setParsedValue(RegionData regData, Vector3D pos) {
		var rpRegData = (RegularPolygonRegionData)regData;
		rpRegData.setCenter(pos);
	}

}
