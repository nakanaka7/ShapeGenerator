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
	@Deprecated
	private RegionData regionData;
	private LinkedHashMap<String, Object> regDataMap = new LinkedHashMap<>();
	private String defaultOffsetKey;
	private Vector3D offset = null;
	
	/**
	 * @param world the world of the selection data
	 * @param regionData the region data of the selection data
	 */
	@Deprecated
	public SelectionData(World world, RegionData regionData) {
		this.world = world;
		this.regionData = regionData;
	}
	
	public SelectionData(World world, String defaultOffsetKey, String... regionDataKeys) {
		this.world = world;
		this.defaultOffsetKey = defaultOffsetKey;
		for(String key : regionDataKeys) {
			this.regDataMap.put(key, null);
		}
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
	 * Get the region data which the selection data has
	 * @return the region data which the selection data has
	 */
	@Deprecated
	public RegionData getRegionData() {
		return regionData;
	}
	
	/**
	 * Get the region data map which the selection data has
	 * @return the region data map which the selection data has
	 */
	public LinkedHashMap<String, Object> getRegionDataMap() {
		return this.regDataMap;
	}
	
	/**
	 * Set a region data to the selection data
	 * @param regionData a region data to set to the selection data
	 */
	@Deprecated
	public void setRegionData(RegionData regionData) {
		this.regionData = regionData;
	}
	
	/**
	 * Returns the offset of the selection
	 * @return the offset of the selection
	 */
	@Deprecated
	public Vector3D getOffsetOld() {
		Vector3D offset = this.offset;
		if(offset == null) {
			offset = this.regionData.getDefaultOffset();
		}
		return offset;
	}
	
	/**
	 * Set an offset
	 * @param offset an offset of the selection
	 */
	@Deprecated
	public void setOffsetOld(Vector3D offset) {
		this.offset = offset;
	}

	/**
	 * Returns the offset of this selection
	 * @return the offset of this selection
	 */
	public Vector3D getOffset() {
		Vector3D offset = this.offset;
		if(offset == null) {
			offset = (Vector3D) this.regDataMap.get(defaultOffsetKey);
		}
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
