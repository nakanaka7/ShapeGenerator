package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.sphereSelSubCommandHandler;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.SphereRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.AbstractPosCommandHandler;

public class CenterCommandHandler extends AbstractPosCommandHandler {

	public CenterCommandHandler() {
		super("center");
	}

	@Override
	protected void setPos(RegionData regData, Vector3D pos) {
		SphereRegionData sphereRegData = (SphereRegionData)regData;
		sphereRegData.setCenter(pos);
	}

}
