package tokyo.nakanaka.shapeGenerator.selectionStrategy.cuboid;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.shapeGenerator.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategy;

/**
 * SelectionStrategy for cuboid selection
 */
public class CuboidSelectionStrategy implements SelectionStrategy {

	@Override
	public Map<String, SelSubCommandHandler> getSelSubCommandHandlerMap() {
		Map<String, SelSubCommandHandler> map = new HashMap<>();
		map.put("pos1", new Pos1CommandHandler());
		map.put("pos2", new Pos2CommandHandler());
		return map;
	}

}
