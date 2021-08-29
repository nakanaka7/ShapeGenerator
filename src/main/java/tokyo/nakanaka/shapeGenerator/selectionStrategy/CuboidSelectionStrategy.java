package tokyo.nakanaka.shapeGenerator.selectionStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.selSubCommandHandler.SelSubCommandHandler;

/**
 * SelectionStrategy for cuboid selection
 */
public class CuboidSelectionStrategy implements SelectionStrategy {

	@Override
	public Map<String, SelSubCommandHandler> getSelSubCommandHandlerMap() {
		HashMap<String, SelSubCommandHandler> map = new HashMap<>();
		map.put("pos1", null);
		map.put("pos2", null);
		return map;
	}

}
