package tokyo.nakanaka.shapeGenerator.selectionStrategy;

import java.util.Map;

import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.selSubCommandHandler.SelSubCommandHandler;

/**
 * Holds methods which is special to a selection shape
 */
public interface SelectionStrategy {
	/**
	 * Get a map which key is "/sg sel" subcommand label and which value is SelSubCommandHandler.
	 * @return a map which key is "/sg sel" subcommand label and which value is SelSubCommandHandler.
	 */
	Map<String, SelSubCommandHandler> getSelSubCommandHandlerMap();
}
