package tokyo.nakanaka.shapeGenerator.regionData;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;

public interface RegionData {
	/**
	 * Returns a bound region from the region data
	 * @return a bound region from the region data
	 */
	BoundRegion3D buildBoundRegion3D();
	
	/**
	 * Returns default offset
	 * @return default offset
	 */
	Vector3D defaultOffset();
	
}
