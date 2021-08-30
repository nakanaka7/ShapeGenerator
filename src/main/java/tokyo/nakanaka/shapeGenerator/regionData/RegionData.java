package tokyo.nakanaka.shapeGenerator.regionData;

import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;

/**
 * Region data that selection data has
 */
public interface RegionData {
	/**
	 * Returns BoundRegion3D object from this data
	 * @return BoundRegion3D object from this data
	 */
	BoundRegion3D createBoundRegion3D();
}
