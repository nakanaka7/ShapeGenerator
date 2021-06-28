package tokyo.nakanaka.selection;

import java.util.Map;

public interface SelectionHandlerFactory {
	ClickBlockHandler getClickBlockHandler();
	Map<String, SelSubCommandHandler> getSelSubCommandHandlers();
	DefaultOffsetHandler getDefaultOffsetHandler();
	RegionStateMessageHandler getRegionStateMessageHandler();
}
