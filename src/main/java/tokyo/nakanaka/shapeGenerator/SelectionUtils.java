package tokyo.nakanaka.shapeGenerator;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeDelegator.SelectionShapeDelegator;

public class SelectionUtils {
	private static Map<SelectionShape, SelectionShapeDelegator> selmap = new HashMap<>();
	
	public static SelectionData newSelectionData(SelectionShape selShape) {
		SelectionData selData = new SelectionData();
		RegionData regData = selmap.get(selShape).newRegionData();
		selData.setRegionData(regData);
		return selData;
	}
	
	/**
	 * Get SelSubCommandHandlerMap
	 * @return SelSubCommandHandlerMap
	 */
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap(SelectionShape selShape) {
		return selmap.get(selShape).selSubCommandHandlerMap();
	}
	
	/**
	 * Returns a short description for left clicking block event
	 * @return a short description for left clicking block event
	 */
	public static String leftClickDescription(SelectionShape selShape) {
		return selmap.get(selShape).leftClickDescription();
	}
	
	/**
	 * Returns a short description for right clicking block event
	 * @return a short description for right clicking block event
	 */
	public static String rightClickDescription(SelectionShape selShape) {
		return selmap.get(selShape).rightClickDescription();
	}
	
	public static void updateRegionDataByLeftClick(SelectionShape selShape, RegionData regionData, BlockVector3D blockPos) {
		//TODO
	}
	
	public static void updateRegionDataByRightClick(SelectionShape selShape, RegionData regionData, BlockVector3D blockPos) {
		//TODO
	}
	
	/**
	 * Returns a selection from the selection data
	 * @param selData a selection data
	 * @return a selection from the selection data
	 * @throws IllegalArgumentException if the selection data cannot create a selection
	 */
	public Selection buildSelection(SelectionShape selShape, SelectionData selData) {
		return selmap.get(selShape).buildSelection(selData);
	}
	
}
