package tokyo.nakanaka.shapeGenerator.regionData;

import java.util.LinkedHashMap;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Diamond;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;

public class DiamondRegionData implements RegionData {
	private Vector3D center;
	private Double radiusX;
	private Double radiusY;
	private Double radiusZ;
	
	public Vector3D getCenter() {
		return center;
	}
	
	public void setCenter(Vector3D center) {
		this.center = center;
	}
	
	public Double getRadiusX() {
		return radiusX;
	}
	public void setRadiusX(Double radiusX) {
		this.radiusX = radiusX;
	}
	
	public Double getRadiusY() {
		return radiusY;
	}
	
	public void setRadiusY(Double radiusY) {
		this.radiusY = radiusY;
	}
	
	public Double getRadiusZ() {
		return radiusZ;
	}
	
	public void setRadiusZ(Double radiusZ) {
		this.radiusZ = radiusZ;
	}
	
	@Override
	public BoundRegion3D buildBoundRegion3D() {
		if(center == null || radiusX == null || radiusY == null || radiusZ == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Diamond(radiusX, radiusY, radiusZ);
		region = Region3Ds.shift(region, center);
		double ubx = center.getX() + radiusX;
		double uby = center.getY() + radiusY;
		double ubz = center.getZ() + radiusZ;
		double lbx = center.getX() - radiusX;
		double lby = center.getY() - radiusY;
		double lbz = center.getZ() - radiusZ;
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Override
	public Vector3D defaultOffset() {
		return center;
	}
	
	@Override
	public LinkedHashMap<String, String> toLinkedHashMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("center", "");
		map.put("radius_x", "");
		map.put("radius_y", "");
		map.put("radius_z", "");
		if(center != null) {
			map.put("center", center.toString());
			map.put("radius_x", String.valueOf(radiusX));
			map.put("radius_y", String.valueOf(radiusY));
			map.put("radius_z", String.valueOf(radiusZ));
		}
		return map;
	}
	
}
