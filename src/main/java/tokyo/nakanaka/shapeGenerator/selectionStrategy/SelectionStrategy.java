package tokyo.nakanaka.shapeGenerator.selectionStrategy;

import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.selSubCommandHandler.SelSubCommandHandler;

/**
 * Holds methods which is special to a selection shape
 */
public interface SelectionStrategy {
	/**
	 * Get SelSubCommandHandler
	 * @return SelSubCommandHandler
	 */
	SelSubCommandHandler getSelSubCommandHandler();
	
}
