package tokyo.nakanaka.selection;

import tokyo.nakanaka.math.Vector3D;

public class SphereRegionBuilder {
	private Vector3D center;
	private double radius;
	
	public Vector3D getCenter() {
		return center;
	}
	
	public void setCenter(Vector3D center) {
		this.center = center;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double radius) {
		if(radius < 0) {
			throw new IllegalArgumentException();
		}
		this.radius = radius;
	}
	
}
