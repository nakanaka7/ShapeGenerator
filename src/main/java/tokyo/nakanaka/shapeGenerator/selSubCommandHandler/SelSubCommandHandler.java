package tokyo.nakanaka.shapeGenerator.selSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public interface SelSubCommandHandler {
	/**
	 * Handles "sg sel" subcommand
	 * @param userData a user data 
	 * @param player a player
	 * @param subArgs arguments for this sub command
	 */
	void onCommand(UserData userData, Player player, String[] subArgs);
	/**
	 * Returns a list for tab complete
	 * @param userData a user data
	 * @param player a player
	 * @param subArgs arguments for this sub command
	 * @return a list for tab complete
	 */
	List<String> onTabComplete(UserData userData, Player player, String[] subArgs);
}
