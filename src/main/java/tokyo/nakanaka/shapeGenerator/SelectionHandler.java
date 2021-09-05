package tokyo.nakanaka.shapeGenerator;

import java.util.Map;

import tokyo.nakanaka.math.BlockVector3D;
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
	 * @return a new selection data for the selection shape
	 */
	public SelectionData newSelectionData(SelectionShape selShape) {
		SelectionData selData = new SelectionData();
		RegionData regData = selStrtgMap.get(selShape).newRegionData();
		selData.setRegionData(regData);
		return selData;
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
	
	/**
	 * Set a first block click position into the region data
	 * @param regData the region data 
	 * @param blockPos the clicked block position 
	 */
	public void setFirstClickData(SelectionShape selShape, RegionData regionData, BlockVector3D blockPos) {
		selStrtgMap.get(selShape).setFirstClickData(regionData, blockPos);
	}
	
	/**
	 * Set data on additional block clicking
	 * @param regData the region data
	 * @param blockPos the clicked block position 
	 */
	public void setAdditionalClickData(SelectionShape selShape, RegionData regionData, BlockVector3D blockPos) {
		selStrtgMap.get(selShape).setAdditionalClickData(regionData, blockPos);
	}
	
}
