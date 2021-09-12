package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;

/**
 * Represents a region which holds all the points which y coordinate is larger than or equals
 * to a specified value
 */
@PublicAPI
public class MinYRegion3D implements Region3D {
	private double minY;

	/**
	 * @param minY a y coordinate which specifies the min boundary along y axis. All the point
	 * which y coordinate is larger than or equals to this value is included the region.
	 */
	public MinYRegion3D(double minY) {
		this.minY = minY;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return y >= this.minY;
	}
	
}
