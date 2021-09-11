package tokyo.nakanaka.shapeGenerator;

import java.util.List;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Item;
import tokyo.nakanaka.NamespacedID;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerDataRepository;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

@PrivateAPI
class SgEventHandler {
	private PlayerDataRepository playerDataRepository;
	private SelectionShapeStrategyRepository shapeStrtgRepo;
	
	SgEventHandler(PlayerDataRepository playerDataRepository, SelectionShapeStrategyRepository shapeStrtgRepo) {
		this.playerDataRepository = playerDataRepository;
		this.shapeStrtgRepo = shapeStrtgRepo;
	}

	/**
	 * Handles a click block event
	 * @param evt a click block event
	 */
	void onClickBlockEvent(ClickBlockEvent evt) {
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
		SelectionShapeStrategy shapeStrtg = this.shapeStrtgRepo.get(selShape);
		//reset the selection builder if the world changes
		World evtWorld = blockPos.world();
		if(!evtWorld.equals(playerData.getSelectionData().world())) {
			SelectionData newSelData = shapeStrtg.newSelectionData(evtWorld);
			playerData.setSelectionData(newSelData);
		}
		//update the selection builder
		SelectionData selData = playerData.getSelectionData();
		BlockVector3D v = new BlockVector3D(blockPos.x(), blockPos.y(), blockPos.z());
		switch(evt.getHandType()) {
			case LEFT_HAND -> shapeStrtg.onLeftClick(selData, v);
			case RIGHT_HAND -> {
				try{
					shapeStrtg.onRightClick(selData, v);
				}catch(IllegalStateException e) {
					player.print(LogColor.RED + "Left click first");
					return;
				}
			}
		}
		//print the selection message
		List<String> lines = MessageUtils.selectionMessage(selShape, selData);
		lines.stream().forEach(player::print);
	}
	
}
