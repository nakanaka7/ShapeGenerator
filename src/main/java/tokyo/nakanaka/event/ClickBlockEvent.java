package tokyo.nakanaka.event;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.ItemStack;
import tokyo.nakanaka.Player;

public class ClickBlockEvent {
	private Player player;
	private BlockPosition blockPos;
	private HandType handType;
	private ItemStack itemStack;
	
	public ClickBlockEvent(Player player, BlockPosition blockPos, HandType handType, ItemStack itemStack) {
		this.player = player;
		this.blockPos = blockPos;
		this.handType = handType;
		this.itemStack = itemStack;
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

	public ItemStack getItemStack() {
		return itemStack;
	}

}
