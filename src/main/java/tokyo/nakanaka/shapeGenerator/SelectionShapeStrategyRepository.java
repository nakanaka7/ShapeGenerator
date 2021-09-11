package tokyo.nakanaka.shapeGenerator;

import java.util.LinkedHashMap;
import java.util.Map;

import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

/**
 * A repository of selection shape strategies
 */
@PrivateAPI
public class SelectionShapeStrategyRepository {
	private Map<SelectionShape, SelectionShapeStrategy> selStrtgMap = new LinkedHashMap<>();
	
	/**
	 * Register 
	 * @param selShape a selection shape
	 * @param selStrtg a selection shape strategy
	 */
	public void register(SelectionShape selShape, SelectionShapeStrategy selStrtg) {
		this.selStrtgMap.put(selShape, selStrtg);
	}

	/**
	 * Returns an array of registered selection shapes
	 * @return an array of registered selection shapes
	 */
	public SelectionShape[] registeredShapes() {
		return this.selStrtgMap.keySet().stream().toArray(SelectionShape[]::new);
	}
	
	/**
	 * Returns a selection shape strategy for the given selection shape
	 * @param selShape a selection shape 
	 * @return a selection shape strategy
	 * @throws IllegalArgumentException if the given selection shape is not registered
	 */
	public SelectionShapeStrategy get(SelectionShape selShape) {
		if(!this.selStrtgMap.containsKey(selShape)) {
			throw new IllegalArgumentException();
		}
		return this.selStrtgMap.get(selShape);
	}
	
}
