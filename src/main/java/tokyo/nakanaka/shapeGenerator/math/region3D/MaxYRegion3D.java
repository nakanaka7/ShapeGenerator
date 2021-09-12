package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;

/**
 * Represents a region which holds all the points which y coordinate is less than or equals
 * to a specified value
 */
@PublicAPI
public class MaxYRegion3D implements Region3D {
	private double maxY;

	/**
	 * @param maxY a y coordinate which specifies the max boundary along y axis. All the point
	 * which y coordinate is less than or equals to this value is included the region.
	 */
	public MaxYRegion3D(double maxY) {
		this.maxY = maxY;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return y <= this.maxY;
	}
	
}
