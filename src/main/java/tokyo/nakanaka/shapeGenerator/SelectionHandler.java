package tokyo.nakanaka.shapeGenerator;

import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
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
	 * Returns a new selection builder for the selection shape
	 * @param selShape a selection shape
	 * @param world a world
	 * @return a new selection builder for the selection shape
	 */
	public SelectionData newSelectionData(SelectionShape selShape, World world) {
		return selStrtgMap.get(selShape).newSelectionData(world);
	}
	
	/**
	 * Returns a selection from the selection data 
	 * @param selShape a selection shape
	 * @param selData a selection data
	 * @return a selection from the selection data 
	 * @throws IllegalStateException if the selection data cannot create a selection
	 */
	public Selection buildSelection(SelectionShape selShape, SelectionData selData) {
		return selStrtgMap.get(selShape).buildSelection(selData);
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
	 * Update the selection data on left block click
	 * @param selShape a selection shape
	 * @param selData the selection data
	 * @param blockPos the block position
	 */
	public void onLeftClick(SelectionShape selShape, SelectionData selData, BlockVector3D blockPos) {
		this.selStrtgMap.get(selShape).onLeftClick(selData, blockPos);
	}
	
	/**
	 * Update the selection data on right block click
	 * @param selShape a selection shape
	 * @param selData the selection data
	 * @param blockPos the block position
	 */
	public void onRightClick(SelectionShape selShape, SelectionData selData, BlockVector3D blockPos) {
		this.selStrtgMap.get(selShape).onRightClick(selData, blockPos);
	}
	
}
