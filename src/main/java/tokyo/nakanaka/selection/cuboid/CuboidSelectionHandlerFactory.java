package tokyo.nakanaka.selection.cuboid;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.selection.ClickBlockHandler;
import tokyo.nakanaka.selection.DefaultOffsetHandler;
import tokyo.nakanaka.selection.RegionStateMessageHandler;
import tokyo.nakanaka.selection.SelSubCommandHandler;
import tokyo.nakanaka.selection.SelectionHandlerFactory;

public class CuboidSelectionHandlerFactory implements SelectionHandlerFactory{

	@Override
	public ClickBlockHandler getClickBlockHandler() {
		return new CuboidClickBlockHandler();
	}

	@Override
	public Map<String, SelSubCommandHandler> getSelSubCommandHandlers() {
		Map<String, SelSubCommandHandler> map = new HashMap<>();
		map.put("pos1", new Pos1CommandHandler());
		map.put("pos2", new Pos2CommandHandler());
		return map;
	}

	@Override
	public DefaultOffsetHandler getDefaultOffsetHandler() {
		return new CuboidDefaultOffsetHandler();
	}

	@Override
	public RegionStateMessageHandler getRegionStateMessageHandler() {
		return new CuboidStateMessageHandler();
	}

}
