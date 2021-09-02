package tokyo.nakanaka.shapeGenerator;

import java.util.LinkedHashMap;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.Vector3D;

/**
 * Selection data that user data has
 */
public class SelectionData {
	private World world;
	private LinkedHashMap<String, Object> regDataMap = new LinkedHashMap<>();
	private String defaultOffsetKey;
	private Vector3D offset = null;
	
	public SelectionData(World world, LinkedHashMap<String, Object> regDataMap, String defaultOffsetKey) {
		this.world = world;
		this.regDataMap = regDataMap;
		this.defaultOffsetKey = defaultOffsetKey;
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
