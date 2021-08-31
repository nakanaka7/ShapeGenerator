package tokyo.nakanaka.shapeGenerator.selectionStrategy;

import java.util.Map;

import tokyo.nakanaka.shapeGenerator.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
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
	/**
	 * Create a BoundRegion3D object from the region data
	 * @param regData the region data
	 * @return a BoundRegion3D object from the region data
	 * @throws IllegalArgumentException if the region data cannot create a BoundRegion3D object
	 */
	BoundRegion3D createBoundRegion3D(RegionData regData);
}
