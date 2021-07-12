package tokyo.nakanaka.selection.selectionStrategy;

import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.region3D.CuboidBoundRegion;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.selSubCommandHandler.SelSubCommandHandler;

public interface SelectionStrategy {
	RegionBuildingData newRegionBuildingData();
	String getLeftClickDescription();
	String getRightClickDescription();
	void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos);
	void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos);
	String getDefaultOffsetLabel();
	List<SelSubCommandHandler> getSelSubCommandHandlers();
	CuboidBoundRegion buildBoundRegion3D(RegionBuildingData data);
}
