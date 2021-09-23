package tokyo.nakanaka.shapeGenerator.math.regionBound;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.math.Vector3D;

/**
 * Represents a bound of a region. The bound must contain all the points of the region
 */
@PrivateAPI
public interface RegionBound {
	/**
	 * Returns the x coordinate of the upper bound about x direction
	 * @return the x coordinate of the upper bound about x direction
	 */
	double upperBoundX();
	/**
	 * Returns the y coordinate of the upper bound about y direction
	 * @return the y coordinate of the upper bound about y direction
	 */
	double upperBoundY();
	/**
	 * Returns the z coordinate of the upper bound about y direction
	 * @return the z coordinate of the upper bound about y direction
	 */
	double upperBoundZ();
	/**
	 * Returns the x coordinate of the lower bound about x direction
	 * @return the x coordinate of the lower bound about x direction
	 */
	double lowerBoundX();
	/**
	 * Returns the y coordinate of the lower bound about y direction
	 * @return the y coordinate of the lower bound about y direction
	 */
	double lowerBoundY();
	/**
	 * Returns the z coordinate of the lower bound about z direction
	 * @return the z coordinate of the lower bound about z direction
	 */
	double lowerBoundZ();
	
	/**
	 * Returns new bound which is shifted  
	 * @param dis a displacement of shift
	 * @return new bound which is shifted
	 */
	RegionBound createShifted(Vector3D dis);
	
	/**
	 * Returns new bound which is scaled along the specified axis with specified factor
	 * @param axis the axis to scale
	 * @param factor a scale factor
	 * @param offset the origin of the transform
	 * @return new bound which is scaled along the specified axis and factor
	 */
	RegionBound createScaled(Axis axis, double factor, Vector3D offset);

	/**
	 * Returns new bound which is mirrored about the specified axis 
	 * @param offset the origin of the transform
	 * @return new bound which is mirrored about the specified axis
	 */
	RegionBound createMirrored(Axis axis, Vector3D offset);
	
	/**
	 * Returns new bound which is rotated about the specified axis by the given degree
	 * @param axis x, y, or z axis, which is used with the offset to specify the rotation axis
	 * @param degree the degree to rotate
	 * @param offset used with the x y, or z axis to specify the rotation axis
	 * @return new bound which is rotated about the specified axis by the given degree
	 */
	RegionBound createRotated(Axis axis, double degree, Vector3D offset);
	
}
