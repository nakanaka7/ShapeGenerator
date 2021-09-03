package tokyo.nakanaka.shapeGenerator;

import java.util.LinkedHashMap;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

/**
 * Selection data that user data has
 */
public class SelectionData {
	private World world;
	private LinkedHashMap<String, Object> regDataMap;
	private RegionData regionData;
	private Vector3D offset;
	
	public SelectionData() {
	}
	
	@Deprecated
	public SelectionData(World world, LinkedHashMap<String, Object> regDataMap, String defaultOffsetKey) {
		this.world = world;
		this.regDataMap = regDataMap;
	}
	
	/**
	 * Get the world which the selection data has
	 * @return the world which the selection data has
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * Set a world to the selection data
	 * @param world a world to set to the selection data
	 */
	public void setWorld(World world) {
		this.world = world;
	}
	
	/**
	 * Get the region data map which the selection data has
	 * @return the region data map which the selection data has
	 */
	public LinkedHashMap<String, Object> getRegionDataMap() {
		return this.regDataMap;
	}
	
	/**
	 * Set a region data map
	 * @param regDataMap a region data map
	 */
	public void setRegionDataMap(LinkedHashMap<String, Object> regDataMap) {
		this.regDataMap = regDataMap;
	}

	/**
	 * Return the region data
	 * @return the region data
	 */
	public RegionData getRegionData() {
		return regionData;
	}

	/**
	 * Set a region data
	 * @param regionData a region data
	 */
	public void setRegionData(RegionData regionData) {
		this.regionData = regionData;
	}

	/**
	 * Returns the offset of this selection
	 * @return the offset of this selection
	 */
	public Vector3D getOffset() {
		return offset;
	}
	
	/**
	 * Set an offset of this selection
	 * @param offset an offset of this selection
	 */
	public void setOffset(Vector3D offset) {
		this.offset = offset;
	}
		
}
