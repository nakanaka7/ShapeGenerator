package tokyo.nakanaka.selection.sphere;

import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelSubCommandHandler;
import tokyo.nakanaka.selection.SelectionStrategy;

public class SphereSelectionStrategy implements SelectionStrategy{

	@Override
	public RegionBuildingData newRegionBuildingData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDefaultOffsetLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SelSubCommandHandler> getSelSubCommandHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoundRegion3D buildBoundRegion3D(RegionBuildingData data) {
		// TODO Auto-generated method stub
		return null;
	}

}
