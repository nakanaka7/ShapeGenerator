package tokyo.nakanaka.selection;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;

public interface ClickBlockHandler {
	void onLeftClickBlock(RegionBuilder builder, Logger logger, BlockVector3D blockPos);
	void onRightClickBlock(RegionBuilder builder, Logger logger, BlockVector3D blockPos);
}
