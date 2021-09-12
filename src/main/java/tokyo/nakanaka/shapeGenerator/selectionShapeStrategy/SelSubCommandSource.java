package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.List;

import tokyo.nakanaka.Player;

/**
 * Gives information of a "/sg sel" subcommand
 * @param <E> Type of parsing object which will be put in a selection data
 */
public interface SelSubCommandSource<E> {
	/**
	 * Returns a label of the "/sg sel" subcommand
	 * @return a label of the "/sg sel" subcommand
	 */
	String subLabel();
	/**
	 * Returns a usage of the "/sg sel" subcommand's arguments
	 * @return a usage of the "/sg sel" subcommand's arguments
	 */
	String subArgsUsage();
	/**
	 * Returns an object which is parsed
	 * @param subArgs the arguments of the subcommand
	 * @return an object which is parsed
	 * @throws IllegalArgumentException if this cannot parse the arguments to an object
	 */
	E parse(Player player, String[] subArgs);
	/**
	 * Returns a list of tab complete for this subcommand
	 * @param player a player
	 * @param subArgs arguments of the subcommand
	 * @return a list of tab complete for this subcommand
	 */
	List<String> onTabComplete(Player player, String[] subArgs);
	
}
