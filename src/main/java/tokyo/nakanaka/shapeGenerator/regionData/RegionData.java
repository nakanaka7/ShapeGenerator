package tokyo.nakanaka.shapeGenerator.regionData;

import tokyo.nakanaka.math.Vector3D;

/**
 * Region data that selection data has
 */
@Deprecated
public interface RegionData {
	/**
	 * Returns a default offset
	 * @return a default offset
	 */
	Vector3D getDefaultOffset();
}
