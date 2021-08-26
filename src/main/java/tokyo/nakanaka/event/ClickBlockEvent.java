package tokyo.nakanaka.event;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.ItemStack;
import tokyo.nakanaka.Player;

/**
 * Represents click block event
 */
public interface ClickBlockEvent {
	
	public Player getPlayer();

	public BlockPosition getBlockPos();

	public HandType getHandType();

	public ItemStack getItemStack();

	public void cancel();
	
}
