package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.selection.selSubCommandHandler.SelSubCommandHandler;

public interface SelectionStrategy {
	RegionBuildingData newRegionBuildingData();
	void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos);
	void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos);
	String getDefaultOffsetLabel();
	List<SelSubCommandHandler> getSelSubCommandHandlers();
	BoundRegion3D buildBoundRegion3D(RegionBuildingData data);
}
