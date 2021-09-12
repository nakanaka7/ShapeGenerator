package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;

/**
 * Represents a region which holds all the points which z coordinate is larger than or equals
 * to a specified value
 */
@PublicAPI
public class MinZRegion3D implements Region3D {
	private double minZ;

	/**
	 * @param minZ a z coordinate which specifies the min boundary along z axis. All the point
	 * which z coordinate is larger than or equals to this value is included the region.
	 */
	public MinZRegion3D(double minZ) {
		this.minZ = minZ;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return z >= this.minZ;
	}
	
}
