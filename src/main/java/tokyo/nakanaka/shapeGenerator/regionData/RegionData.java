package tokyo.nakanaka.shapeGenerator.regionData;

import java.util.LinkedHashMap;

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
	
	/**
	 * Returns the map which holds this object's information
	 * @return the map which holds this object's information
	 */
	LinkedHashMap<String, String> toLinkedHashMap();
	
}
