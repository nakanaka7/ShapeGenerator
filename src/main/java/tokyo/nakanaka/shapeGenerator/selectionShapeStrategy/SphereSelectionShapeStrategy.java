package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.SphereRegionData;

public class SphereSelectionShapeStrategy implements SelectionShapeStrategy{

	@Override
	public RegionData newRegionData() {
		return new SphereRegionData();
	}
	
	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set radius by the center coordinates";
	}
	
	@Override
	public void setFirstClickData(RegionData regData, BlockVector3D blockPos) {
		SphereRegionData sphereRegData = (SphereRegionData)regData;
		Vector3D center = blockPos.toVector3D();
		sphereRegData.setCenter(center);
	}

	@Override
	public void setAdditionalClickData(RegionData regData, BlockVector3D blockPos) {
		SphereRegionData sphereRegData = (SphereRegionData)regData;
		Vector3D center = sphereRegData.getCenter();
		Vector3D pos = blockPos.toVector3D();
		double radius = Math.floor(pos.negate(center).getAbsolute()) + 0.5;
		sphereRegData.setRadius(radius);
	}

	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		return map;
	}
	
}
