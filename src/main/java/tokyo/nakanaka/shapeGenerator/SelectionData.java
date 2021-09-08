package tokyo.nakanaka.shapeGenerator;

import java.util.LinkedHashMap;
import java.util.List;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.Vector3D;

/**
 * A selection data
 */
public class SelectionData {
	private World world;
	private String dafualtOffsetLabel;
	private LinkedHashMap<String, Object> extraDataMap = new LinkedHashMap<String, Object>();
	private Vector3D customOffset;
	
	/**
	 * Constructs a selection data
	 * @param world a world
	 * @param defaultOffsetLabel a default offset label, which must be included in extraDataLabels
	 * @param extraDataLabels labels which are used to store extra data
	 */
	public SelectionData(World world, String defaultOffsetLabel, String... extraDataLabels) {
		this.world = world;
		this.dafualtOffsetLabel = defaultOffsetLabel;
		for(String e : extraDataLabels) {
			this.extraDataMap.put(e, null);
		}
	}
	
	/**
	 * Returns the world of the data
	 * @return the world of the data
	 */
	public World world() {
		return world;
	}
	
	/**
	 * Returns the default offset label
	 * @return the default offset label
	 */
	public String dafualtOffsetLabel() {
		return dafualtOffsetLabel;
	}

	/**
	 * Returns a list for the extra data labels
	 * @return a list for the extra data labels
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
		}else {
			return (Vector3D) this.extraDataMap.get(dafualtOffsetLabel);
		}
	}
	
}
