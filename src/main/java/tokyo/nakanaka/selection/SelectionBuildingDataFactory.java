package tokyo.nakanaka.selection;

import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.SelectionShapeNew;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategy;
@Deprecated
public class SelectionBuildingDataFactory {
	private Map<SelectionShapeNew, SelectionStrategy> strategyMap;
	
	public SelectionBuildingDataFactory(Map<SelectionShapeNew, SelectionStrategy> strategyMap) {
		this.strategyMap = strategyMap;
	}

	public SelectionBuildingData create(World world, SelectionShapeNew shape) {
		RegionBuildingData regionData = this.strategyMap.get(shape).newRegionBuildingData();
		return new SelectionBuildingData(world, regionData);
	}
	
}
