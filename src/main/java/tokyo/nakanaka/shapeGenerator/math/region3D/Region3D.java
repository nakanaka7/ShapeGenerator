package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;

/**
 * Represents 3 dimensional region
 */
@PublicAPI
public interface Region3D {
	/**
	 * Returns true if this region contains (x, y, z), otherwise false
	 * @param x x coordinate of the position
	 * @param y y coordinate of the position
	 * @param z z coordinate of the position 
	 * @return true if this region contains (x, y, z), otherwise false
	 */
	boolean contains(double x, double y, double z);
}
