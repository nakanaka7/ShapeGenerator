package tokyo.nakanaka.selection.cuboid;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.ClickBlockHandler;

public class CuboidClickBlockHandler implements ClickBlockHandler<CuboidRegionBuilder>{
	public void onLeftClickBlock(CuboidRegionBuilder cuboidBuilder, Logger logger, BlockVector3D blockPos) {
		cuboidBuilder.setPos1(new Vector3D(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
	}

	public void onRightClickBlock(CuboidRegionBuilder cuboidBuilder, Logger logger, BlockVector3D blockPos) {
		cuboidBuilder.setPos2(new Vector3D(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
	}
}
