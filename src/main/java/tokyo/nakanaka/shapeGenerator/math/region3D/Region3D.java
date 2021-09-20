package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.Vector3D;

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
	
	/**
	 * Returns a region which is given by shifting this region
	 * @param dx the displacement along x axis
	 * @param dy the displacement along y axis
	 * @param dz the displacement along z axis
	 * @return a region which is given by shifting this region
	 */
	default Region3D shift(double dx, double dy, double dz) {
		return new ShiftedRegion3D(this, dx, dy, dz);
	}
	
	/**
	 * Returns a region which is given by shifting this region
	 * @param dis the displacement of the shifting
	 * @return a region which is given by shifting this region
	 */
	default Region3D shift(Vector3D dis) {
		return this.shift(dis.getX(), dis.getY(), dis.getZ());
	}
	
}
