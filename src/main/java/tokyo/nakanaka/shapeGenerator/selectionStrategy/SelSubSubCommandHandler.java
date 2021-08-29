package tokyo.nakanaka.shapeGenerator.selectionStrategy;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.SelectionData;

public interface SelSubSubCommandHandler {
	/**
	 * Handles "/sg sel" subcommand subcommand
	 * @param selData a selection data that the user data holds
	 * @param player player that run the command
	 * @param subArgs arguments of the subcommand
	 */
	void onCommand(SelectionData selData, Player player, String[] subArgs);
	/**
	 * Return a list of tab complete for "/sg sel" subcommand subcommand
	 * @param selData a selection data that the user data holds
	 * @param player player that run the command
	 * @param subArgs arguments of the subcommand
	 * @return a list of tab complete for "/sg sel" subcommand
	 */
	List<String> onTabComplete(SelectionData selData, Player player, String[] subArgs);

}
