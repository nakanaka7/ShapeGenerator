package tokyo.nakanaka.selection.cuboid;

import java.util.Arrays;
import java.util.List;
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
	public List<SelSubCommandHandler> getSelSubCommandHandlers() {
		return Arrays.asList(new Pos1CommandHandler(), new Pos2CommandHandler());
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
