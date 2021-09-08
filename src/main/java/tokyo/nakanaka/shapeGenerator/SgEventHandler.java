package tokyo.nakanaka.shapeGenerator;

import java.util.List;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Item;
import tokyo.nakanaka.NamespacedID;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerDataRepository;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

public class SgEventHandler {
	private PlayerDataRepository playerDataRepository;
	private SelectionHandler selHandler;
	
	public SgEventHandler(PlayerDataRepository playerDataRepository, SelectionHandler selHandler) {
		this.playerDataRepository = playerDataRepository;
		this.selHandler = selHandler;
	}

	/**
	 * Handles a click block event
	 * @param evt a click block event
	 */
	public void onClickBlockEvent(ClickBlockEvent evt) {
		//ignore the event if the clicking item is not "minecraft:blaze_rod"
		Item item = evt.getItemStack().getItem();
		if(!item.equals(new Item(new NamespacedID("minecraft", "blaze_rod")))) {
			return;
		}
		evt.cancel();
		Player player = evt.getPlayer();
		BlockPosition blockPos = evt.getBlockPos();
		PlayerData playerData = this.playerDataRepository.preparePlayerData(player);
		SelectionShape selShape = playerData.getSelectionShape();
		//reset the selection builder if the world changes
		World evtWorld = blockPos.world();
		if(!evtWorld.equals(playerData.getSelectionBuilder().world())) {
			SelectionData newSelBuilder = this.selHandler.newSelectionData(selShape, evtWorld);
			playerData.setSelectionBuilder(newSelBuilder);
		}
		//update the selection builder
		SelectionData selBuilder = playerData.getSelectionBuilder();
		RegionData regData = selBuilder.getRegionData();
		BlockVector3D v = new BlockVector3D(blockPos.x(), blockPos.y(), blockPos.z());
		switch(evt.getHandType()) {
			case LEFT_HAND -> regData.onLeftClick(v);
			case RIGHT_HAND -> regData.onRightClick(v);
		}
		//print the selection message
		List<String> lines = MessageUtils.selectionMessage(selShape, selBuilder);
		lines.stream().forEach(player::print);
	}
	
}
