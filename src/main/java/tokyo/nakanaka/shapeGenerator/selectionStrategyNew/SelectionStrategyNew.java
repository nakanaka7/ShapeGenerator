package tokyo.nakanaka.shapeGenerator.selectionStrategyNew;

import java.util.List;
import java.util.Map;

import tokyo.nakanaka.shapeGenerator.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

/**
 * Holds methods which is special to a selection shape
 */
public interface SelectionStrategyNew {
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
	/**
	 * Returns key list for region data. It does not contain "offset".
	 * @return key list for region data. It does not contain "offset".
	 */
	List<String> regionDataKeyList();
	/**
	 * A key of the region data for the default offset
	 */
	String defaultOffsetKey();
	/**
	 * Returns a BoundRegion3D object from the region data
	 * @param regData the region data
	 * @return a BoundRegion3D object from the region data
	 * @throws IllegalArgumentException if the region data cannot create a BoundRegion3D object
	 */
	BoundRegion3D buildBoundRegion3D(Map<String, Object> regData);
}
