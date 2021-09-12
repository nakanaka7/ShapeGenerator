package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;

/**
 * Represents a region which holds all the points which z coordinate is less than or equals
 * to a specified value
 */
@PublicAPI
public class MaxZRegion3D implements Region3D {
	private double maxZ;

	/**
	 * @param maxZ a z coordinate which specifies the max boundary along z axis. All the point
	 * which z coordinate is less than or equals to this value is included the region.
	 */
	public MaxZRegion3D(double maxZ) {
		this.maxZ = maxZ;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return z <= this.maxZ;
	}
	
}
