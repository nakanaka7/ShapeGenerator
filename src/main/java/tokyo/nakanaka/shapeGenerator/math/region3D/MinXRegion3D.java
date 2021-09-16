package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;

/**
 * Represents a region which holds all the points which x coordinate is larger than or equals
 * to a specified value
 */
@PublicAPI
public class MinXRegion3D implements Region3D {
	private double minX;

	/**
	 * @param minX a x coordinate which specifies the min boundary along x axis. All the point
	 * which x coordinate is larger than or equals to this value is included the region.
	 */
	public MinXRegion3D(double minX) {
		this.minX = minX;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return x >= this.minX;
	}

}
