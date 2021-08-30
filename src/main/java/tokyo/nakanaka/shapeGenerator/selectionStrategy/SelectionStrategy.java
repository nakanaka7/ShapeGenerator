package tokyo.nakanaka.shapeGenerator.selectionStrategy;

import java.util.Map;

import tokyo.nakanaka.shapeGenerator.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

/**
 * Holds methods which is special to a selection shape
 */
public interface SelectionStrategy {
	/**
	 * Get SelSubCommandHandlerMap
	 * @return SelSubCommandHandlerMap
	 */
	Map<String, SelSubCommandHandler> getSelSubCommandHandlerMap();
	/**
	 * Create new region data
	 * @return new region data
	 */
	RegionData newRegionData();
	
}
