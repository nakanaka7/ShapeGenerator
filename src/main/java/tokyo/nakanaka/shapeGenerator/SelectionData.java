package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

/**
 * Selection data that user data has
 */
public class SelectionData {
	private World world;
	private RegionData regionData;
	
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
	public RegionData getRegionData() {
		return regionData;
	}
	
	/**
	 * Set a region data to the selection data
	 * @param regionData a region data to set to the selection data
	 */
	public void setRegionData(RegionData regionData) {
		this.regionData = regionData;
	}
	
}
