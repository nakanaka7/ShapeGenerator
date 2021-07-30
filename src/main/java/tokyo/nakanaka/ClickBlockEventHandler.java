package tokyo.nakanaka;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.player.User;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.world.World;

public class ClickBlockEventHandler {
	private SelectionStrategySource selStraSource;
	private SelectionMessenger selMessenger = new SelectionMessenger();
	
	public ClickBlockEventHandler(SelectionStrategySource selStraSource) {
		this.selStraSource = selStraSource;
	}

	public void onLeftClickBlock(User user, int x, int y, int z) {
		BlockVector3D blockPos = new BlockVector3D(x, y, z);
		World world = user.getWorld();
		SelectionShape selShape = user.getSelectionShape();
		SelectionBuildingData selData = user.getSelectionBuildingData();
		SelectionStrategy selStrategy = this.selStraSource.get(selShape);
		if(!world.equals(selData.getWorld())) {
			RegionBuildingData regionData = selStrategy.newRegionBuildingData();
			selData = new SelectionBuildingData(world, regionData);
			user.setSelectionBuildingData(selData);
		}
		RegionBuildingData regionData = selData.getRegionData();
		Logger logger = user.getLogger();
		selStrategy.onLeftClickBlock(regionData, logger, blockPos);
		this.selMessenger.printSelection(logger, selShape, selData, selStrategy.getDefaultOffsetLabel());
	}
	
	public void onRightClickBlock(User user, int x, int y, int z) {
		BlockVector3D blockPos = new BlockVector3D(x, y, z);
		World world = user.getWorld();
		SelectionShape selShape = user.getSelectionShape();
		SelectionBuildingData selData = user.getSelectionBuildingData();
		SelectionStrategy selStrategy = this.selStraSource.get(selShape);
		if(!world.equals(selData.getWorld())) {
			RegionBuildingData regionData = selStrategy.newRegionBuildingData();
			selData = new SelectionBuildingData(world, regionData);
			user.setSelectionBuildingData(selData);
		}
		RegionBuildingData regionData = selData.getRegionData();
		Logger logger = user.getLogger();
		selStrategy.onRightClickBlock(regionData, logger, blockPos);
		this.selMessenger.printSelection(logger, selShape, selData, selStrategy.getDefaultOffsetLabel());
	}
}
