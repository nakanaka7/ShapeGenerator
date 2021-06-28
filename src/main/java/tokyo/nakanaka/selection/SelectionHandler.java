package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;

public interface SelectionHandler {
	void onLeftClickBlock(RegionBuilder builder, Logger logger, BlockVector3D blockPos);
	void onRightClickBlock(RegionBuilder builder, Logger logger, BlockVector3D blockPos);
	List<String> getCommandLabels();
	boolean onCommand(RegionBuilder builder, Logger logger, BlockVector3D playerPos, String label, String[] args);
	List<String> onTabComplete(String label, String[] args);	
	String getDefaultOffsetLabel();
	Vector3D getDefaultOffset(RegionBuilder builder);
	List<Pair<String, String>> onMessage(RegionBuilder builder);
}
