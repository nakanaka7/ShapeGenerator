package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.math.Vector3D;

public class RegionBuildingData {
	private List<String> labelList;
	private Map<String, DataType> typeMap;
	private Map<String, Vector3D> vectorMap;
	private Map<String, Double> doubleMap;
	
	public static enum DataType{
		VECTOR3D,
		DOUBLE;
	}
	
	public RegionBuildingData(Builder builder) {
		this.labelList = builder.labelList;
		this.typeMap = builder.typeMap;
		this.vectorMap = builder.vectorMap;
		this.doubleMap = builder.doubleMap;
	}
	
	public static class Builder{
		private List<String> labelList = new ArrayList<>();
		private Map<String, DataType> typeMap = new HashMap<>();
		private Map<String, Vector3D> vectorMap = new HashMap<>();
		private Map<String, Double> doubleMap = new HashMap<>();
		
		public Builder addDataTag(String label, DataType type) {
			this.labelList.add(label);
			this.typeMap.put(label, type);
			return this;
		}
		
		public RegionBuildingData build() {
			return new RegionBuildingData(this);
		}
		
	}
	
	public List<String> getLabels(){
		return this.labelList;
	}
	
	public Object get(String label) {
		if(this.vectorMap.containsKey(label)) {
			return this.vectorMap.get(label);
		}else if(this.doubleMap.containsKey(label)) {
			return this.doubleMap.get(label);
		}else {
			return null;
		}
	}
	
	public DataType getDataType(String label) {
		return this.typeMap.get(label);
	}
	
	public void putVector3D(String label, Vector3D value) {
		if(!this.labelList.contains(label) || this.typeMap.get(label) != DataType.VECTOR3D) {
			throw new IllegalArgumentException();
		}
		this.vectorMap.put(label, value);
	}
	
	public Vector3D getVector3D(String label) {
		return this.vectorMap.get(label);
	}
	
	public void putDouble(String label, double value) {
		if(!this.labelList.contains(label) || this.typeMap.get(label) != DataType.DOUBLE) {
			throw new IllegalArgumentException();
		}
		this.doubleMap.put(label, value);
	}
	
	public Double getDouble(String label) {
		return this.doubleMap.get(label);
	}
	
}
