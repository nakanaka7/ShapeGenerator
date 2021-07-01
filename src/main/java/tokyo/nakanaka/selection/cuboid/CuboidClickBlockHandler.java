package tokyo.nakanaka.selection.cuboid;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.ClickBlockHandler;
import tokyo.nakanaka.selection.RegionBuilder;
import tokyo.nakanaka.selection.RegionBuildingData;

@Deprecated
public class CuboidClickBlockHandler implements ClickBlockHandler {
	
	public void onLeftClickBlock(RegionBuilder builder, Logger logger, BlockVector3D blockPos) {
		CuboidRegionBuilder cuboidBuilder = (CuboidRegionBuilder)builder;
		cuboidBuilder.setPos1(new Vector3D(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
	}

	public void onRightClickBlock(RegionBuilder builder, Logger logger, BlockVector3D blockPos) {
		CuboidRegionBuilder cuboidBuilder = (CuboidRegionBuilder)builder;
		cuboidBuilder.setPos2(new Vector3D(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
	}

	@Override
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		data.putVector3D("pos1", blockPos.toVector3D());
	}

	@Override
	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		data.putVector3D("pos2", blockPos.toVector3D());
	}
	
}
