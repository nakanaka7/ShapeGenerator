package tokyo.nakanaka.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tokyo.nakanaka.shapeGenerator.MapHolder;
import tokyo.nakanaka.shapeGenerator.NamespacedID;

public class Block {
	private NamespacedID id;
	private Map<String, String> stateMap;
	
	private Block(NamespacedID id, Map<String, String> stateMap) {
		this.id = id;
		this.stateMap = new HashMap<>(stateMap);
	}

	/**
	 * @param str
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Block valueOf(String str) {
		if(!str.contains(":")) {
			str = "minecraft:" + str;
		}
		MapHolder holder = MapHolder.valueOf(str);
		NamespacedID id = NamespacedID.valueOf(holder.getName());
		return new Block(id, holder.getMap());
	}
	
	public NamespacedID getId() {
		return id;
	}

	public Map<String, String> getStateMap() {
		return stateMap;
	}
	
	/**
	 * Get a string representation of the block state. For example, minecraft:brick_stairs[facing=east,half=bottom].
	 * @return a string of the block state
	 */
	public String getBlockStateString() {
		String str = this.id.toString();
		if(this.stateMap.size() != 0) {
			List<String> kv = new ArrayList<>();
			for(Entry<String, String> entry : this.stateMap.entrySet()) {
				kv.add(entry.getKey() + "=" + entry.getValue());
			}
			str = str + "[" + String.join(",", kv) + "]";
		}
		return str;
	}
	
}
