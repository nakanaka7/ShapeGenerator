package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public interface SelSubCommandHandler {
	String getLabel();
	String getDescription();
	List<ParameterHelp> getParameterHelpList();
	/**
	 * @return true if data changed else false
	 */
	boolean onCommand(RegionBuildingData data, Player player, String[] subArgs);
	/**
	 * Returns a list for tab complete
	 * @param user
	 * @param player a player
	 * @param subArgs arguments for this sub command
	 * @return a list for tab complete
	 */
	List<String> onTabComplete(UserData user, Player player, String[] subArgs);
}
