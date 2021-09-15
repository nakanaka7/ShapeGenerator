package tokyo.nakanaka.shapeGenerator.math.region2D;

import tokyo.nakanaka.annotation.PublicAPI;

/**
 * Represents a region on 2D(x-y) space
 */
@PublicAPI
public interface Region2D {
	/**
	 * Checks the given point is included in the region
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @return true if the point is included in the region, otherwise false
	 */
	boolean contains(double x, double y);
	
}
