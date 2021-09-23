package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.Vector3D;

/**
 * Represents 3 dimensional region
 */
@PublicAPI
public interface Region3D {
	/**
	 * Returns true if this region contains a given point, otherwise false
	 * @param x x coordinate of the position of the point
	 * @param y y coordinate of the position of the point
	 * @param z z coordinate of the position of the point
	 * @return true if this region contains (x, y, z), otherwise false
	 */
	boolean contains(double x, double y, double z);
	
	/**
	 * Returns true if this region contains a given point, otherwise false
	 * @param pos position of the point 
	 * @return true if this region contains a given point, otherwise false
	 */
	default boolean contains(Vector3D pos) {
		return this.contains(pos.getX(), pos.getY(), pos.getZ());
	}
	
	/**
	 * Returns a region which is given by shifting this region
	 * @param dx the displacement along x axis
	 * @param dy the displacement along y axis
	 * @param dz the displacement along z axis
	 * @return a region which is given by shifting this region
	 */
	default Region3D createShifted(double dx, double dy, double dz) {
		return new ShiftedRegion3D(this, dx, dy, dz);
	}
	
	/**
	 * Returns a region which is given by shifting this region
	 * @param dis the displacement of the shifting
	 * @return a region which is given by shifting this region
	 */
	default Region3D createShifted(Vector3D dis) {
		return this.createShifted(dis.getX(), dis.getY(), dis.getZ());
	}
	
	/**
	 * Returns a region which is given by scaling this region
	 * @param axis an axis which gives the direction of the scaling
	 * @param factor a scaling factor
	 * @param offset an offset of the scaling
	 * @return a region which is given by scaling this region
	 * @throws IllegalArgumentException if factor is zero
	 */
	default Region3D createScaled(Axis axis, double factor, Vector3D offset) {
		Region3D a = this.createShifted(offset.multiply(-1));
		Region3D b = new ScaledRegion3D(a, axis, factor);
		return b.createShifted(offset);
	}
	
	/**
	 * Returns a region which is given by mirroring this region
	 * @param axis an axis which gives the direction of the mirroring
	 * @param offset an offset which gives the plane of the mirroring 
	 * @return a region which is given by mirroring this region
	 */
	default Region3D createMirrored(Axis axis, Vector3D offset) {
		Region3D a = this.createShifted(offset.multiply(-1));
		Region3D b = new MirroredRegion3D(a, axis);
		return b.createShifted(offset);
	}
	
	/**
	 * Returns a region which is given by rotating this region
	 * @param axis an axis which is parallel to the rotating axis
	 * @param degree an angular of rotating
	 * @param offset a point on rotating axis
	 * @return a region which is given by rotating this region
	 */
	default Region3D createRotated(Axis axis, double degree, Vector3D offset) {
		Region3D a = this.createShifted(offset.multiply(-1));
		Region3D b = new RotatedRegion3D(a, axis, degree);
		return b.createShifted(offset);
	}
	
}
