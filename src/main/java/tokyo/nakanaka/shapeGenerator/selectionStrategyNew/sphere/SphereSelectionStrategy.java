package tokyo.nakanaka.shapeGenerator.selectionStrategyNew.sphere;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.SphereBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.Sphere;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionStrategyNew.SelSubCommandHandlerNew;
import tokyo.nakanaka.shapeGenerator.selectionStrategyNew.SelectionStrategyNew;

public class SphereSelectionStrategy implements SelectionStrategyNew {

	@Override
	public Map<String, SelSubCommandHandlerNew> getSelSubCommandHandlerMap() {
		Map<String, SelSubCommandHandlerNew> map = new HashMap<>();
		map.put("center", new CenterCommandHandler());
		map.put("radius", new RadiusCommandHandler());
		return map;
	}

	@Override
	public RegionData newRegionData() {
		return new SphereRegionData();
	}

	@Override
	public BoundRegion3D createBoundRegion3D(RegionData regData) {
		if(!(regData instanceof SphereRegionData sphereRegData)) {
			throw new IllegalArgumentException();
		}
		Vector3D center = sphereRegData.getCenter();
		double radius = sphereRegData.getRadius();
		Region3D region = new Sphere(radius);
		region = Region3Ds.shift(region, center);
		return new SphereBoundRegion(region, center, radius);
	}

	@Override
	public List<String> regionDataKeyList() {
		return List.of("center", "radius");
	}

	@Override
	public String defaultOffsetKey() {
		return "center";
	}

	@Override
	public BoundRegion3D buildBoundRegion3D(Map<String, Object> regData) {
		Vector3D center = (Vector3D)regData.get("center");
		Double radius = (Double)regData.get("radius");
		if(center == null || radius == null) {
			throw new IllegalArgumentException();
		}
		Region3D region = new Sphere(radius);
		region = Region3Ds.shift(region, center);
		return new SphereBoundRegion(region, center, radius);
	}

}
