package tokyo.nakanaka.shapeGenerator.regionData;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.math.Vector3D;

public class RegularPolygonRegionData implements RegionData {
	private Vector3D center;
	private Double radius;
	private Integer side;
	private Double thickness;
	private Axis axis;
	
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
	
	public Integer getSide() {
		return side;
	}
	
	public void setSide(Integer side) {
		this.side = side;
	}
	
	public Double getThickness() {
		return thickness;
	}
	
	public void setThickness(Double thickness) {
		this.thickness = thickness;
	}
	
	public Axis getAxis() {
		return axis;
	}
	
	public void setAxis(Axis axis) {
		this.axis = axis;
	}
	
}
