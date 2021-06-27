package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;

public interface SelCommandHandler {
	CommandHelp getCommandHelp();
	void onCommand(RegionBuilder builder, Logger logger, BlockVector3D playerPos, String[] args);
	List<String> onTabComplete(String[] args);
}
