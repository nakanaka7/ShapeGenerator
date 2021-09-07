package tokyo.nakanaka.shapeGenerator;

import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

/**
 * Holds methods which depends on selection shape 
 */
public class SelectionHandler {
	private Map<SelectionShape, SelectionShapeStrategy> selStrtgMap;
	
	/**
	 * Construct the handler
	 * @param selStrtgMap a map which key is selection shape and which value is selection shape strategy.
	 */
	public SelectionHandler(Map<SelectionShape, SelectionShapeStrategy> selStrtgMap) {
		this.selStrtgMap = selStrtgMap;
	}

	/**
	 * Returns a new selection data for the selection shape
	 * @param selShape a selection shape
	 * @param world a world
	 * @return a new selection data for the selection shape
	 */
	public SelectionData newSelectionData(SelectionShape selShape, World world) {
		RegionData regData = selStrtgMap.get(selShape).newRegionData();
		return new SelectionData(world, regData);
	}
	
	/**
	 * Get SelSubCommandHandlerMap
	 * @return SelSubCommandHandlerMap
	 */
	public Map<String, SubCommandHandler> selSubCommandHandlerMap(SelectionShape selShape) {
		return selStrtgMap.get(selShape).selSubCommandHandlerMap();
	}
	
	/**
	 * Returns a short description for left clicking block event
	 * @return a short description for left clicking block event
	 */
	public String leftClickDescription(SelectionShape selShape) {
		return selStrtgMap.get(selShape).leftClickDescription();
	}
	
	/**
	 * Returns a short description for right clicking block event
	 * @return a short description for right clicking block event
	 */
	public String rightClickDescription(SelectionShape selShape) {
		return selStrtgMap.get(selShape).rightClickDescription();
	}
	
}
