package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.LineRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

public class LineSelectionShapeStrategy implements SelectionShapeStrategy {
	
	public RegionData newRegionData() {
		return new LineRegionData();
	}
	
	@Override
	public String leftClickDescription() {
		return "Set pos1";
	}

	@Override
	public String rightClickDescription() {
		return "Set pos2";
	}
	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		return map;
	}
	
}
