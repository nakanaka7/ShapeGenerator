package tokyo.nakanaka.shapeGenerator.selectionStrategy.sphere;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.SphereBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.Sphere;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategy;

public class SphereSelectionStrategy implements SelectionStrategy {

	@Override
	public Map<String, SelSubCommandHandler> getSelSubCommandHandlerMap() {
		Map<String, SelSubCommandHandler> map = new HashMap<>();
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

}
