package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.LinearTransformation;
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
		return this.createShifted(new Vector3D(dx, dy, dz));
	}
	
	/**
	 * Returns a region which is given by shifting this region
	 * @param dis the displacement of the shifting
	 * @return a region which is given by shifting this region
	 */
	default Region3D createShifted(Vector3D dis) {
		if(this instanceof AffineTransformedRegion3D affReg) {
			Region3D newOriginal = affReg.originalRegion3D();
			LinearTransformation newTrans = affReg.linearTransformation();
			Vector3D newDis = affReg.displacement().add(dis);
			return new AffineTransformedRegion3D(newOriginal, newTrans, newDis);
		}else {
			return new AffineTransformedRegion3D(this, LinearTransformation.IDENTITY, dis);
		}
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
		LinearTransformation trans = LinearTransformation.ofScale(axis, factor);
		return createLinearTransformed(trans, offset);
	}
	
	/**
	 * Returns a region which is given by mirroring this region
	 * @param axis an axis which gives the direction of the mirroring
	 * @param offset an offset which gives the plane of the mirroring 
	 * @return a region which is given by mirroring this region
	 */
	default Region3D createMirrored(Axis axis, Vector3D offset) {
		LinearTransformation trans = LinearTransformation.ofMirror(axis);
		return createLinearTransformed(trans, offset);
	}
	
	/**
	 * Returns a region which is given by rotating this region
	 * @param axis an axis which is parallel to the rotating axis
	 * @param degree an angular of rotating
	 * @param offset a point on rotating axis
	 * @return a region which is given by rotating this region
	 */
	default Region3D createRotated(Axis axis, double degree, Vector3D offset) {
		LinearTransformation trans = LinearTransformation.ofRotation(axis, degree);
		return createLinearTransformed(trans, offset);
	}
	
	/**
	 * Returns a region which is given by applying a linear transformation about an offset
	 * @param trans a linear transformation which is applied to this region about the offset
	 * @param offset an offset for the linear transformation. The offset point will be unchanged by the linear transformation.
	 * @throws IllegalArgumentException if trans is a singular one
	 * Returns a region which is given by applying a linear transformation about an offset
	 */
	default Region3D createLinearTransformed(LinearTransformation trans, Vector3D offset) {
		Region3D region = this.createShifted(offset.multiply(-1));
		region = region.createLinearTransformed(trans);
		return region.createShifted(offset);
	}
	
	/**
	 * Returns a region which is given by applying a linear transformation about the origin
	 * @param trans a linear transformation which is applied to this region
	 * @throws IllegalArgumentException if trans is a singular one
	 * Returns a region which is given by applying a linear transformation about the origin
	 */
	default Region3D createLinearTransformed(LinearTransformation trans) {
		if(this instanceof AffineTransformedRegion3D affReg) {
			LinearTransformation newTrans = trans.multipy(affReg.linearTransformation());
			Vector3D newDis = trans.apply(affReg.displacement());
			return new AffineTransformedRegion3D(affReg.originalRegion3D(), newTrans, newDis);
		}else {
			return new AffineTransformedRegion3D(this, trans, Vector3D.ORIGIN);
		}
	}
	
}
