package tokyo.nakanaka.shapeGenerator;

import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

/**
 * Create new selection data
 */
public class SelectionDataCreator {
	private Map<SelectionShape, SelectionShapeStrategy> selStrtgMap;

	public SelectionDataCreator(Map<SelectionShape, SelectionShapeStrategy> selStrtgMap) {
		this.selStrtgMap = selStrtgMap;
	}
	
	/**
	 * Returns new selection data corresponding to the selection shape which has the given world
	 * @param shape the selection shape
	 * @param world a world
	 * @return new selection data corresponding to the selection shape which has the given world
	 */
	public SelectionData newSelectionData(SelectionShape shape, World world) {
		RegionData regData = this.selStrtgMap.get(shape).newRegionData();
		return new SelectionData(world, regData);
	}

}
