package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;

public interface SelectionHandler {
	void onLeftClickBlock(RegionBuilder builder, Logger logger, BlockVector3D blockPos);
	void onRightClickBlock(RegionBuilder builder, Logger logger, BlockVector3D blockPos);
	String getDefaultOffsetLabel();
	Vector3D getDefaultOffset(RegionBuilder builder);
	List<String> getCommandLabelList();
	boolean onCommand(RegionBuilder builder, Logger logger, String label, BlockVector3D playerPos, String[] args);
	List<String> onTabComplete(String label, String[] args);
	List<Pair<String, String>> onRegionMessage();
	BoundRegion3D build(RegionBuilder builder);
}
