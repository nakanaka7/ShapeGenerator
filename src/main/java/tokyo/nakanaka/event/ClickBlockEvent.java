package tokyo.nakanaka.event;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.legacy.Item;

public class ClickBlockEvent {
	private Player player;
	private BlockPosition blockPos;
	private HandType handType;
	private Item item;
	
	public ClickBlockEvent(Player player, BlockPosition blockPos, HandType handType, Item item) {
		this.player = player;
		this.blockPos = blockPos;
		this.handType = handType;
		this.item = item;
	}
	
	public static enum HandType{
		LEFT_HAND,
		RIGHT_HAND;
	}

	public Player getPlayer() {
		return player;
	}

	public BlockPosition getBlockPos() {
		return blockPos;
	}

	public HandType getHandType() {
		return handType;
	}

	public Item getItem() {
		return item;
	}

}
