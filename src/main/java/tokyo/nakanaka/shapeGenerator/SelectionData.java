package tokyo.nakanaka.shapeGenerator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

/**
 * A Class which build a selection
 */
@SuppressWarnings("deprecation")
public class SelectionData {
	private World world;
	private String defualtOffsetLabel;
	private RegionData regData;
	private LinkedHashMap<String, Object> extraDataMap = new LinkedHashMap<String, Object>();
	private Vector3D customOffset;
	
	public SelectionData(World world, RegionData regData) {
		this.world = world;
		this.regData = regData;
	}
	
	/**
	 * Constructs a selection data
	 * @param world a world
	 * @param defaultOffsetLabel a default offset label, which must be included in extraDataLabels
	 * @param extraDataLabels labels which are used to store extra data
	 */
	public SelectionData(World world, String defaultOffsetLabel, String... extraDataLabels) {
		this.world = world;
		this.defualtOffsetLabel = defaultOffsetLabel;
		for(String e : extraDataLabels) {
			this.extraDataMap.put(e, null);
		}
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
	public String defualtOffsetLabel() {
		if(this.defualtOffsetLabel == null) {
			return this.regData.defaultOffsetLabel();
		}else {
			return this.defualtOffsetLabel;
		}
	}
	
	/**
	 * Return the region data
	 * @return the region data
	 */
	public RegionData getRegionData() {
		return regData;
	}
	
	/**
	 * Return an array of the extra data labels
	 * @return an array of the extra data labels
	 */
	public String[] extraDataLabels() {
		return this.extraDataMap.keySet().stream().toArray(String[]::new);
	}
	
	/**
	 * Returns a list of the extra data labels
	 * @return a list of the extra data labels
	 */
	public List<String> extraDataLabelList() {
		return this.extraDataMap.keySet().stream().toList();
	}
	
	/**
	 * Returns extra data for the label
	 * @param label a label 
	 * @return extra data for the label
	 */
	public Object getExtraData(String label) {
		return this.extraDataMap.get(label);
	}
	
	/**
	 * Set a extra data for the label
	 * @param label a label
	 * @param data a data
	 * @throws IllegalArgumentException if the label is not given in the constructor
	 */
	public void setExtraData(String label, Object data) {
		if(!this.extraDataMap.containsKey(label)) {
			throw new IllegalArgumentException();
		}
		this.extraDataMap.put(label, data);
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
		}else if(this.regData != null){
			return this.regData.defaultOffset();
		}else {
			return (Vector3D) this.extraDataMap.get(this.defualtOffsetLabel);
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
		if(this.regData != null) {
			for(Entry<String, String> e : this.regData.toLinkedHashMap().entrySet()) {
				map.put(e.getKey(), e.getValue());
			}
		}else {
			for(String k : this.extraDataMap.keySet()) {
				Object o = this.extraDataMap.get(k);
				String v = "";
				if(o != null) {
					v = o.toString();
				}
				map.put(k, v);
			}
		}
		String offset;
		if(this.customOffset != null) {
			offset = this.customOffset.toString();
		}else if(this.defualtOffsetLabel != null){
			offset = this.defualtOffsetLabel;
		}else {
			offset = this.regData.defaultOffsetLabel();
		}
		map.put("offset", offset);
		return map;
	}
		
}
