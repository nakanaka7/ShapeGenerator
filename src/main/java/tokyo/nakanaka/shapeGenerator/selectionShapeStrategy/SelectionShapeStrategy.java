package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.shapeGenerator.SelectionData;

/**
 * Holds SelectionShape delegating methods
 */
public interface SelectionShapeStrategy {

	default String clickDescription() {
		return  "";
	}

	/**
	 * Returns a short description for left clicking block event
	 * @return a short description for left clicking block event
	 */
	default String leftClickDescription(){
		return "";
	}
	
	/**
	 * Returns a short description for right clicking block event
	 * @return a short description for right clicking block event
	 */
	default String rightClickDescription(){
		return "";
	}
	
	/**
	 * Update the selection data on left block click
	 * @param selData the selection data
	 * @param blockPos the block position
	 * @throws IllegalStateException if this click operation cannot be handled
	 */
	void onLeftClick(SelectionData selData, BlockVector3D blockPos);
	
	/**
	 * Update the selection data on right block click
	 * @param selData the selection data
	 * @param blockPos the block position
	 * @throws IllegalStateException if this click operation cannot be handled
	 */
	void onRightClick(SelectionData selData, BlockVector3D blockPos);

}
