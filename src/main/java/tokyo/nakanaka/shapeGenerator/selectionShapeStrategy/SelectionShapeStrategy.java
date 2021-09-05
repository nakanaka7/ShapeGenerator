package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.Map;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;

/**
 * Holds SelectionShape delegating methods
 */
public interface SelectionShapeStrategy {
	
	/**
	 * Returns new region data of a selection
	 * @return new region data of a selection
	 */
	RegionData newRegionData();
		
	/**
	 * Returns a map which key is "/sg sel" subcommand's subLabel and which value is SelSubCommandHandler object
	 * @return a map which key is "/sg sel" subcommand's subLabel and which value is SelSubCommandHandler object
	 */
	Map<String, SelSubCommandHandler> selSubCommandHandlerMap();

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
	 * Set a first block click position into the region data
	 * @param regData the region data 
	 * @param blockPos the clicked block position 
	 */
	void setFirstClickData(RegionData regData, BlockVector3D blockPos);
	
	/**
	 * Set data on additional block clicking
	 * @param regData the region data
	 * @param blockPos the clicked block position 
	 */
	void setAdditionalClickData(RegionData regData, BlockVector3D blockPos);
			
}
