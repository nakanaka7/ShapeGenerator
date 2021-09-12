package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;

/**
 * Represents a region which holds all the points which x coordinate is less than or equals
 * to a specified value
 */
@PublicAPI
public class MaxXRegion3D implements Region3D {
	private double maxX;

	/**
	 * @param maxX a x coordinate which specifies the max boundary along x axis. All the point
	 * which x coordinate is less than or equals to this value is included the region.
	 */
	public MaxXRegion3D(double maxX) {
		this.maxX = maxX;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return x <= this.maxX;
	}
	
}
