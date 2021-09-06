package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.diamondSelSubCommandHandler;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.DiamondRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.PosCommandHandler;

/**
 * Handles "/sg sel center" command in diamond selection mode
 */
public class CenterCommandHandler extends PosCommandHandler {

	public CenterCommandHandler() {
		super("center");
	}

	@Override
	protected RegionData newRegionData() {
		return new DiamondRegionData();
	}

	@Override
	protected void setParsedValue(RegionData regData, Vector3D pos) {
		var diamondRegData = (DiamondRegionData)regData;
		diamondRegData.setCenter(pos);
	}
	
}
