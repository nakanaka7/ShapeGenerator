package tokyo.nakanaka.shapeGenerator.selectionStrategy.sphere;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

/**
 * Region data of sphere selection
 */
public class SphereRegionData implements RegionData {
	private Vector3D center;
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
	
}
