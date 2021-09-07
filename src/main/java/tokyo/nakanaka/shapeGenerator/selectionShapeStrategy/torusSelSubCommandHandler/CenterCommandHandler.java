package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.torusSelSubCommandHandler;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TorusRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.PosCommandHandler;

/**
 * Handles "/sg sel center" command in torus selection mode
 */
public class CenterCommandHandler extends PosCommandHandler {

	public CenterCommandHandler() {
		super("center");
	}

	@Override
	protected RegionData newRegionData() {
		return new TorusRegionData();
	}

	@Override
	protected void setParsedValue(RegionData regData, Vector3D parsed) {
		var torusRegData = (TorusRegionData)regData;
		torusRegData.setCenter(parsed);
	}
	
}
