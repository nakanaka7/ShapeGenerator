package tokyo.nakanaka.shapeGenerator;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

/**
 * Holds methods which depends on selection shape 
 */
public class SelectionHandler {
	private Map<SelectionShape, SelectionShapeStrategy> selStrtgMap = new HashMap<>();
	
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
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap(SelectionShape selShape) {
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
	
	/**
	 * Returns a selection from the selection data
	 * @param selData a selection data
	 * @return a selection from the selection data
	 * @throws IllegalArgumentException if the selection data cannot create a selection
	 */
	public Selection buildSelection(SelectionShape selShape, SelectionData selData) {
		SelectionShapeStrategy s = selStrtgMap.get(selShape);
		RegionData regData = selData.getRegionData();
		BoundRegion3D boundReg = s.buildBoundRegion3D(regData);
		Vector3D offset = selData.getOffset();
		if(offset == null) {
			offset = s.defaultOffset(regData);
		}
		return new Selection(selData.getWorld(), boundReg, offset);
	}
	
}
