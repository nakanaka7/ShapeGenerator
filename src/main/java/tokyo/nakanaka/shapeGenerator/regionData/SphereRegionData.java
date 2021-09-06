package tokyo.nakanaka.shapeGenerator.regionData;

import java.util.LinkedHashMap;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.SphereBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.Sphere;

public class SphereRegionData implements RegionData {
	private Vector3D center;
	private Double radius;
	
	public Vector3D getCenter() {
		return center;
	}
	
	public void setCenter(Vector3D center) {
		this.center = center;
	}
	
	public Double getRadius() {
		return radius;
	}
	
	public void setRadius(Double radius) {
		this.radius = radius;
	}
	
	@Override
	public void onLeftClick(BlockVector3D blockPos) {
		this.center = blockPos.toVector3D();
		this.radius = null;
	}

	@Override
	public void onRightClick(BlockVector3D blockPos) {
		if(this.center == null) {
			throw new IllegalStateException();
		}
		Vector3D pos = blockPos.toVector3D();
		double radius = Math.floor(pos.negate(this.center).getAbsolute()) + 0.5;
		this.radius = radius;
	}
	
	@Override
	public BoundRegion3D buildBoundRegion3D() {
		if(center == null || radius == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Sphere(radius);
		region = Region3Ds.shift(region, center);
		return new SphereBoundRegion(region, center, radius);
	}

	@Override
	public Vector3D defaultOffset() {
		return center;
	}
	
	@Override
	public LinkedHashMap<String, String> toLinkedHashMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("center", "");
		map.put("radius", "");
		if(center != null) {
			map.put("center", center.toString());
		}
		if(radius != null) {
			map.put("radius", String.valueOf(radius));
		}
		return map;
	}

}
