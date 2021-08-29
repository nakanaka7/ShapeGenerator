package tokyo.nakanaka.selection;

import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.shapeGenerator.SelectionShape;

public class SelectionBuildingDataFactory {
	private Map<SelectionShape, SelectionStrategy> strategyMap;
	
	public SelectionBuildingDataFactory(Map<SelectionShape, SelectionStrategy> strategyMap) {
		this.strategyMap = strategyMap;
	}

	public SelectionBuildingData create(World world, SelectionShape shape) {
		RegionBuildingData regionData = this.strategyMap.get(shape).newRegionBuildingData();
		return new SelectionBuildingData(world, regionData);
	}
	
}
