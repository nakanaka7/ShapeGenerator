package tokyo.nakanaka;

import java.util.HashMap;
import java.util.Map;
import tokyo.nakanaka.math.Vector3D;

public class ValuePool {
	private Map<String, Integer> intMap = new HashMap<>();
	private Map<String, Double> doubleMap = new HashMap<>();
	private Map<String, String> stringMap = new HashMap<>();
	private Map<String, Vector3D> vectorMap = new HashMap<>();
	
	public void putInt(String name, int value) {
		this.intMap.put(name, value);
	}
	
	public Integer getInt(String name) {
		return this.intMap.get(name);
	}
	
	public void putDouble(String name, double value) {
		this.doubleMap.put(name, value);
	}
	
	public Double getDouble(String name) {
		return this.doubleMap.get(name);
	}
	
	public void putString(String name, String value) {
		this.stringMap.put(name, value);
	}
	
	public String getString(String name) {
		return this.stringMap.get(name);
	}
	
	public void putVector3D(String name, Vector3D value) {
		this.vectorMap.put(name, value);
	}
	
	public Vector3D getVector3D(String name) {
		return this.vectorMap.get(name);
	}
	
}
