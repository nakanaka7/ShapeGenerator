package tokyo.nakanaka.event;

import java.util.UUID;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Item;
import tokyo.nakanaka.MainFunctions;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.event.ClickBlockEvent.HandType;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.player.User;
import tokyo.nakanaka.player.UserRepository;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;

public class ClickBlockEventListener {
	private UserRepository userRepo;
	private SelectionStrategySource selStrtgSource;
	private static SelectionMessenger selMessenger = new SelectionMessenger();
	
	public ClickBlockEventListener(UserRepository userRepo, SelectionStrategySource selStrtgSource) {
		this.userRepo = userRepo;
		this.selStrtgSource = selStrtgSource;
	}
	
	public void onClickBlockEvent(ClickBlockEvent evt) {
		Player player = evt.getPlayer();
		BlockPosition blockPos = evt.getBlockPos();
		HandType handType = evt.getHandType();
		Item item = evt.getItem();
		if(item != Item.BLAZE_ROD) {
			return;
		}
		UUID uid = player.getUniqueID();
		User user = this.userRepo.getHumanUser(uid);
		if(user == null) {
			user = new User(uid);
			this.userRepo.registerHumanUser(user);
			MainFunctions.setDefaultSelection(this.selStrtgSource, user);
		}
		user.setLogger(player);
		SelectionShape selShape = user.getSelectionShape();
		SelectionBuildingData selData = user.getSelectionBuildingData();
		SelectionStrategy selStrategy = this.selStrtgSource.get(selShape);
		World world = blockPos.world();
		if(!world.equals(selData.getWorld())) {
			RegionBuildingData regionData = selStrategy.newRegionBuildingData();
			selData = new SelectionBuildingData(world, regionData);
			user.setSelectionBuildingData(selData);
		}
		RegionBuildingData regionData = selData.getRegionData();
		BlockVector3D pos = new BlockVector3D(blockPos.x(), blockPos.y(), blockPos.z());
		switch(handType) {
			case LEFT_HAND -> {
				selStrategy.onLeftClickBlock(regionData, player, pos);
			}
			case RIGHT_HAND -> {
				selStrategy.onRightClickBlock(regionData, player, pos);
			}
		}
		selMessenger.printSelection(player, selShape, selData, selStrategy.getDefaultOffsetLabel());
	}
	
}
