package tokyo.nakanaka.shapeGenerator.math.regionBound;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.math.Vector3D;

/**
 * Represents a region which is bound by a larger region. The second region must hold all the points of the first region.
 */
@PrivateAPI
public interface RegionBound {
	double upperBoundX();
	double upperBoundY();
	double upperBoundZ();
	double lowerBoundX();
	double lowerBoundY();
	double lowerBoundZ();
	
	/**
	 * Returns new bound region which is shifted  
	 * @param dis a displacement of shift
	 * @return new bound region which is shifted
	 */
	RegionBound shift(Vector3D dis);
	
	/**
	 * Returns new bound region which is scaled along the specified axis with specified factor
	 * @param axis the axis to scale
	 * @param factor a scale factor
	 * @param offset the origin of the transform
	 * @return new bound region which is scaled along the specified axis and factor
	 */
	RegionBound scale(Axis axis, double factor, Vector3D offset);

	/**
	 * Returns new bound region which is mirrored about the specified axis 
	 * @param offset the origin of the transform
	 * @return new bound region which is mirrored about the specified axis
	 */
	RegionBound mirror(Axis axis, Vector3D offset);
	
	/**
	 * Returns new bound region which is rotated about the specified axis by the given degree
	 * @param axis x, y, or z axis, which is used with the offset to specify the rotation axis
	 * @param degree the degree to rotate
	 * @param offset used with the x y, or z axis to specify the rotation axis
	 * @return new bound region which is rotated about the specified axis by the given degree
	 */
	RegionBound rotate(Axis axis, double degree, Vector3D offset);
	
}
