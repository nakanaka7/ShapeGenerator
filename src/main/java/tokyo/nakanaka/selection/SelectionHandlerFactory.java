package tokyo.nakanaka.selection;

import java.util.List;

public interface SelectionHandlerFactory {
	ClickBlockHandler getClickBlockHandler();
	List<SelSubCommandHandler> getSelSubCommandHandlers();
	DefaultOffsetHandler getDefaultOffsetHandler();
	RegionStateMessageHandler getRegionStateMessageHandler();
}
