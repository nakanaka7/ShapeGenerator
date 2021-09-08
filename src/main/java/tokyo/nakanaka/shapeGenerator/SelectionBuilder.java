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
	 * Returns the default offset label
	 * @return the default offset label
	 */
	public String dafualtOffsetLabel() {
		return this.regData.defaultOffsetLabel();
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
	 * Clear custom offset
	 */
	public void clearCustomOffset() {
		this.customOffset = null;
	}
	
	/**
	 * Returns true if this data has custom offset, otherwise false
	 * @return true if this data has custom offset, otherwise false
	 */
	public boolean hasCustomOffset() {
		return this.customOffset != null;
	}
	
	/**
	 * Returns a offset(nullable). If this data has custom offset, return it. Otherwise return a default offset. 
	 * @return a offset(nullable). If this data has custom offset, return it. Otherwise return a default offset. 
	 */
	public Vector3D getOffset() {
		if(this.customOffset != null) {
			return this.customOffset;
		}else {
			return this.regData.defaultOffset();
		}
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
