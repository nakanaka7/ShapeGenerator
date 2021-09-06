package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.Map;

import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

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
			
}
