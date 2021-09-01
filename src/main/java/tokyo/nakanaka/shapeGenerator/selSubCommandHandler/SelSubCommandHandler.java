package tokyo.nakanaka.shapeGenerator.selSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public interface SelSubCommandHandler {
	String getDescription();
	List<ParameterHelp> getParameterHelpList();
	/**
	 * @return true if data changed else false
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
