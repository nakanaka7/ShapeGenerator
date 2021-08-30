package tokyo.nakanaka.shapeGenerator.selectionStrategy.sphere;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.shapeGenerator.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategy;

public class SphereSelectionStrategy implements SelectionStrategy {

	@Override
	public Map<String, SelSubCommandHandler> getSelSubCommandHandlerMap() {
		Map<String, SelSubCommandHandler> map = new HashMap<>();
		map.put("center", new CenterCommandHandler());
		map.put("radius", new RadiusCommandHandler());
		return map;
	}

	@Override
	public RegionData newRegionData() {
		return new SphereRegionData();
	}

}
