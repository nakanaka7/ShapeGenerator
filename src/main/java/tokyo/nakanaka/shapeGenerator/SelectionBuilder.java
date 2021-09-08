package tokyo.nakanaka.shapeGenerator;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

/**
 * A Class which build a selection
 */
public class SelectionBuilder {
	private World world;
	private RegionData regData;
	private Vector3D customOffset;
	
	public SelectionBuilder(World world, RegionData regData) {
		this.world = world;
		this.regData = regData;
	}
	
	/**
	 * Get the world which the selection data has
	 * @return the world which the selection data has
	 */
	public World world() {
		return world;
	}
	
	/**
	 * Return the region data
	 * @return the region data
	 */
	public RegionData getRegionData() {
		return regData;
	}
	
	/**
	 * Set a custom offset
	 * @param customOffset a custom offset
	 */
	public void setCustomOffset(Vector3D customOffset) {
		this.customOffset = customOffset;
	}
	
	/**
	 * Returns a selection from the selection data
	 * @return a selection from the selection data
	 * @throws IllegalStateException if the selection data cannot create a selection
	 */
	@Deprecated
	public Selection build() {
		BoundRegion3D boundReg = regData.buildBoundRegion3D();
		Vector3D offset = this.customOffset;
		if(offset == null) {
			offset = regData.defaultOffset();
		}
		return new Selection(this.world, boundReg, offset);
	}
	
	/**
	 * Returns the map which holds this object's information
	 * @return the map which holds this object's information
	 */
	LinkedHashMap<String, String> toLinkedHashMap() {
		var map = new LinkedHashMap<String, String>();
		for(Entry<String, String> e : this.regData.toLinkedHashMap().entrySet()) {
			map.put(e.getKey(), e.getValue());
		}
		String offset;
		if(this.customOffset != null) {
			offset = this.customOffset.toString();
		}else {
			offset = this.regData.defaultOffsetLabel();
		}
		map.put("offset", offset);
		return map;
	}
		
}
