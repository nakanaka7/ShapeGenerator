package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.selSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.SelectionData;

/**
 * Handles "/sg sel" subcommand
 */
public interface SelSubCommandHandler {
	/**
	 * Handles "/sg sel" subcommand
	 * @param selData a selection data that the user data holds
	 * @param player player that run the command
	 * @param args arguments of the subcommand
	 */
	void onCommand(SelectionData selData, Player player, String[] args);
	/**
	 * Return a list of tab complete for "/sg sel" subcommand
	 * @param selData a selection data that the user data holds
	 * @param player player that run the command
	 * @param args arguments of the subcommand
	 * @return a list of tab complete for "/sg sel" subcommand
	 */
	List<String> onTabComplete(SelectionData selData, Player player, String[] args);
}
