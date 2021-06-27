package tokyo.nakanaka.selection;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;

public interface ClickBlockHandler<B extends RegionBuilder> {
	void onLeftClickBlock(B builder, Logger logger, BlockVector3D blockPos);
	void onRightClickBlock(B builder, Logger logger, BlockVector3D blockPos);
}
