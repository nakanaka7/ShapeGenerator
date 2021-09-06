package tokyo.nakanaka.shapeGenerator;

import java.util.List;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Item;
import tokyo.nakanaka.NamespacedID;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.event.HandType;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelSubCommandHandlerUtils;
import tokyo.nakanaka.shapeGenerator.user.UserData;
import tokyo.nakanaka.shapeGenerator.user.UserDataRepository;

public class SgEventHandler {
	private UserDataRepository userDataRepository;
	private SelectionHandler selHandler;
	
	public SgEventHandler(UserDataRepository userDataRepository, SelectionHandler selHandler) {
		this.userDataRepository = userDataRepository;
		this.selHandler = selHandler;
	}

	/**
	 * Handles a click block event
	 * @param evt a click block event
	 */
	public void onClickBlockEvent(ClickBlockEvent evt) {
		//cancel the event if the clicked item is not "minecraft:blaze_rod"
		Item item = evt.getItemStack().getItem();
		if(!item.equals(new Item(new NamespacedID("minecraft", "blaze_rod")))) {
			return;
		}
		evt.cancel();
		Player player = evt.getPlayer();
		BlockPosition blockPos = evt.getBlockPos();
		UserData userData = this.userDataRepository.prepareUserData(player);
		SelectionShape selShape = userData.getSelectionShape();
		//reset the selection data if the world changes or clicked type is left hand
		World evtWorld = blockPos.world();
		if(!evtWorld.equals(userData.getSelectionData().getWorld()) || evt.getHandType() == HandType.LEFT_HAND) {
			SelectionData newSelData = this.selHandler.newSelectionData(selShape);
			newSelData.setWorld(evtWorld);
			userData.setSelectionData(newSelData);
		}
		//update the selection data
		SelectionData selData = userData.getSelectionData();
		RegionData regData = selData.getRegionData();
		BlockVector3D v = new BlockVector3D(blockPos.x(), blockPos.y(), blockPos.z());
		switch(evt.getHandType()) {
			case LEFT_HAND -> this.selHandler.setFirstClickData(selShape, regData, v);
			case RIGHT_HAND -> this.selHandler.setAdditionalClickData(selShape, regData, v);
		}
		//print the selection message
		List<String> lines = SelSubCommandHandlerUtils.selectionMessage(selShape, selData);
		lines.stream().forEach(player::print);
	}
	
}
