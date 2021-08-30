package tokyo.nakanaka.shapeGenerator.selectionStrategy.sphere;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.SphereBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.Sphere;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

/**
 * Region data of sphere selection
 */
public class SphereRegionData implements RegionData {
	private Vector3D center = Vector3D.ORIGIN;
	private double radius;
	
	/**
	 * Get the center
	 * @return the center
	 */
	public Vector3D getCenter() {
		return center;
	}
	
	/**
	 * Set a center
	 * @param center a center
	 */
	public void setCenter(Vector3D center) {
		this.center = center;
	}
	
	/**
	 * Get the radius
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}
	
	/**
	 * Set a radius
	 * @param radius a radius
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public BoundRegion3D createBoundRegion3D() {
		Region3D region = new Sphere(radius);
		region = Region3Ds.shift(region, center);
		return new SphereBoundRegion(region, center, radius);

	}
	
}
