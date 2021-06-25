package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;

public interface RegionBuilder {
	void onLeftClickBlock(Logger logger, BlockVector3D blockPos);
	void onRightClickBlock(Logger logger, BlockVector3D blockPos);
	boolean onCommand(Logger logger, BlockVector3D playerPos, String label, String[] args);
	List<String> onLabelList();
	List<String> onTabComplete(String label, String[] args);
	List<String> getStateLines();
	BoundRegion3D build();
}
