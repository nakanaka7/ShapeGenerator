package tokyo.nakanaka.shapeGenerator.regionData;

import tokyo.nakanaka.math.Vector3D;

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
	
}
