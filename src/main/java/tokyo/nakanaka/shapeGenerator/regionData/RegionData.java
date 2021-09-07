package tokyo.nakanaka.shapeGenerator.regionData;

import java.util.LinkedHashMap;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;

public interface RegionData {
	
	/**
	 * Update the region data on a left block click
	 * @param pos the clicked block position
	 */
	void onLeftClick(BlockVector3D blockPos);
	
	/**
	 * Update the region data on a right block click
	 * @param pos the clicked block position
	 * @throws IllegalStateException if this operation is not acceptable.
	 * 	This illegal state has to resolve by additive onLeftClick() operation. 
	 */
	void onRightClick(BlockVector3D blockPos);
	
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
	 * Returns default offset label
	 * @return default offset label
	 */
	String defaultOffsetLabel();
	
	/**
	 * Returns the map which holds this object's information
	 * @return the map which holds this object's information
	 */
	LinkedHashMap<String, String> toLinkedHashMap();
	
}
