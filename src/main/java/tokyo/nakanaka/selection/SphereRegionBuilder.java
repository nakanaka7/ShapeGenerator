package tokyo.nakanaka.selection;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.Region3Ds;
import tokyo.nakanaka.math.region3D.SphereRegion3D;

public class SphereRegionBuilder {
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
	
	public void setRadius(double radius) {
		if(radius < 0) {
			throw new IllegalArgumentException();
		}
		this.radius = radius;
	}
	
	/**
	 * @throws IllegalStateException
	 */
	public Region3D build() {
		if(this.center == null || this.radius == null) {
			throw new IllegalStateException();
		}
		Region3D region = new SphereRegion3D(this.radius);
		return Region3Ds.shift(region, this.center);
	}
	
}
