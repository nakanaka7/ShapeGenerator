package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;

public interface SelSubCommandHandler {
	String getLabel();
	BranchCommandHelp getCommandHelp();
	String getDescription();
	String getUsage();
	/**
	 * @return true if data changed else false
	 */
	boolean onCommand(RegionBuildingData data, Logger logger, BlockVector3D playerPos, String[] args);
	List<String> onTabComplete(String[] args);
}
