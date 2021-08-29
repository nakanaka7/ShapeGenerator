package tokyo.nakanaka.shapeGenerator.selectionStrategy;

import java.util.Map;

import tokyo.nakanaka.shapeGenerator.SelSubCommandHandler;

/**
 * Holds methods which is special to a selection shape
 */
public interface SelectionStrategy {
	/**
	 * Get SelSubCommandHandlerMap
	 * @return SelSubCommandHandlerMap
	 */
	Map<String, SelSubCommandHandler> getSelSubCommandHandlerMap();
	
}
