package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.sphereSelSubCommandHandler;

import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.SphereRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;

public class RadiusCommandHandler extends LengthCommandHandler {

	public RadiusCommandHandler() {
		super("radius");
	}

	@Override
	protected RegionData newRegionData() {
		return new SphereRegionData();
	}

	@Override
	protected void setLength(RegionData regData, Double length) {
		SphereRegionData spRegData = (SphereRegionData)regData;
		spRegData.setRadius(length);
	}
	
}
