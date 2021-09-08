package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

/**
 * Holds SelectionShape delegating methods
 */
public interface SelectionShapeStrategy {
	
	/**
	 * Returns new selection data
	 * @param world a world of the selection data
	 */
	default SelectionData newSelectionData(World world) {
		return new SelectionData(world, newRegionData());
	}
	
	/**
	 * Returns new region data of a selection
	 * @return new region data of a selection
	 */
	@Deprecated
	RegionData newRegionData();
	
	@SuppressWarnings("deprecation")
	default Selection buildSelection(SelectionData selData) {
		return selData.build();
	}
	
	/**
	 * Returns a map which key is "/sg sel" subcommand's subLabel and which value is SubCommandHandler object
	 * @return a map which key is "/sg sel" subcommand's subLabel and which value is SubCommandHandler object
	 */
	Map<String, SubCommandHandler> selSubCommandHandlerMap();

	/**
	 * Returns a short description for left clicking block event
	 * @return a short description for left clicking block event
	 */
	String leftClickDescription();
	
	/**
	 * Returns a short description for right clicking block event
	 * @return a short description for right clicking block event
	 */
	String rightClickDescription();
	
	/**
	 * Update the selection data on left block click
	 * @param selData the selection data
	 * @param blockPos the block position
	 */
	default void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.getRegionData().onLeftClick(blockPos);
	}
	
	/**
	 * Update the selection data on right block click
	 * @param selData the selection data
	 * @param blockPos the block position
	 */
	default void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		selData.getRegionData().onRightClick(blockPos);
	}
	
}
