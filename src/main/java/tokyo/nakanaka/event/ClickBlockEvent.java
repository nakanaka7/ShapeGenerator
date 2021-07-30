package tokyo.nakanaka.event;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Item;
import tokyo.nakanaka.commandSender.PlayerCommandSender;

public class ClickBlockEvent {
	private PlayerCommandSender player;
	private BlockPosition blockPos;
	private HandType handType;
	private Item item;
	
	public ClickBlockEvent(PlayerCommandSender player, BlockPosition blockPos, HandType handType, Item item) {
		this.player = player;
		this.blockPos = blockPos;
		this.handType = handType;
		this.item = item;
	}
	
	public static enum HandType{
		LEFT_HAND,
		RIGHT_HAND;
	}

	public PlayerCommandSender getPlayer() {
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
