package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.SphereRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.sphereSelSubCommandHandler.CenterCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.sphereSelSubCommandHandler.RadiusCommandHandler;

public class SphereSelectionShapeStrategy implements SelectionShapeStrategy{

	@Override
	public RegionData newRegionData() {
		return new SphereRegionData();
	}
	
	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set radius by the center coordinates";
	}
	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put("center", new CenterCommandHandler());
		map.put("radius", new RadiusCommandHandler());
		return map;
	}
	
}
