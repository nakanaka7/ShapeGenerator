package tokyo.nakanaka.selection.sphere;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.Region3Ds;
import tokyo.nakanaka.math.region3D.SphereRegion3D;
import tokyo.nakanaka.selection.RegionBuilder;

public class SphereRegionBuilder implements RegionBuilder{
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

	@Override
	public BoundRegion3D buildRegion() {
		if(this.center == null) {
			throw new IllegalStateException();
		}
		Region3D region = new SphereRegion3D(this.radius);
		region = Region3Ds.shift(region, this.center);
		double ubx = center.getX() + radius;
		double uby = center.getY() + radius;
		double ubz = center.getZ() + radius;
		double lbx = center.getX() - radius;
		double lby = center.getY() - radius;
		double lbz = center.getZ() - radius;
		return new BoundRegion3D(region, ubx, uby, ubz, lbx, lby, lbz);
	}
	
}
