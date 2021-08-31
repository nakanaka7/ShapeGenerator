package tokyo.nakanaka.shapeGenerator.selectionStrategy;

import java.util.Map;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;

public interface SelectionStrategy {
	RegionBuildingData newRegionBuildingData();
	String getLeftClickDescription();
	String getRightClickDescription();
	void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos);
	void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos);
	String getDefaultOffsetKey();
	/**
	 * Returns a map which key is "/sg sel" subcommand's subLabel and which value is SelSubCommandHandler object
	 * @return a map which key is "/sg sel" subcommand's subLabel and which value is SelSubCommandHandler object
	 */
	Map<String, SelSubCommandHandler> getSelSubCommandHandlerMap();
	BoundRegion3D buildBoundRegion3D(RegionBuildingData data);
}
