package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public interface SelSubCommandHandler {
	String getLabel();
	String getDescription();
	List<ParameterHelp> getParameterHelpList();
	/**
	 * @return true if data changed else false
	 */
	boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args);
	List<String> onTabComplete(UserData user, String[] args);
}
