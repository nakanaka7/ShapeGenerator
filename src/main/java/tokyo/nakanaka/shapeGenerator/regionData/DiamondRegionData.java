package tokyo.nakanaka.shapeGenerator.regionData;

import tokyo.nakanaka.math.Vector3D;

public class DiamondRegionData implements RegionData {
	private Vector3D center;
	private Double radius_x;
	private Double radius_y;
	private Double radius_z;
	
	public Vector3D getCenter() {
		return center;
	}
	
	public void setCenter(Vector3D center) {
		this.center = center;
	}
	
	public Double getRadius_x() {
		return radius_x;
	}
	public void setRadius_x(Double radius_x) {
		this.radius_x = radius_x;
	}
	
	public Double getRadius_y() {
		return radius_y;
	}
	
	public void setRadius_y(Double radius_y) {
		this.radius_y = radius_y;
	}
	
	public Double getRadius_z() {
		return radius_z;
	}
	
	public void setRadius_z(Double radius_z) {
		this.radius_z = radius_z;
	}
		
}
