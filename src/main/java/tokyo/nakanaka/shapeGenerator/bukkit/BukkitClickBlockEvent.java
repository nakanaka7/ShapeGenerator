package tokyo.nakanaka.shapeGenerator.bukkit;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerInteractEvent;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.ItemStack;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.bukkit.BukkitFunctions;
import tokyo.nakanaka.bukkit.BukkitWorld;
import tokyo.nakanaka.bukkit.commandSender.BukkitPlayer;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.event.HandType;

/**
 * ClickBlockEvent for bukkit
 */
@PrivateAPI
class BukkitClickBlockEvent implements ClickBlockEvent {
	private Player player;
	private BlockPosition blockPos;
	private HandType handType;
	private ItemStack itemStack;
	private Cancellable evt0;
	
	/**
	 * Construct a click block event from PlayerInteractEvent evt0
	 * @param evt0 an original event
	 * @throws IllegalArgumentException if the event is not a click block event
	 */
	public BukkitClickBlockEvent(PlayerInteractEvent evt0) {
		this.handType = switch(evt0.getAction()) {
		case LEFT_CLICK_BLOCK -> this.handType = HandType.LEFT_HAND;
		case RIGHT_CLICK_BLOCK -> this.handType = HandType.RIGHT_HAND;
		default -> throw new IllegalArgumentException();
		};
		org.bukkit.entity.Player player0 = evt0.getPlayer();
		this.player = new BukkitPlayer(player0);
		Location loc = evt0.getClickedBlock().getLocation();
		World world = new BukkitWorld(player0.getServer(), loc.getWorld());
		this.blockPos = new BlockPosition(world, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		org.bukkit.inventory.ItemStack itemStack0 = player0.getInventory().getItemInMainHand();
		this.itemStack = BukkitFunctions.convertItemStack(itemStack0);
		this.evt0 = evt0;
	}
	
	@Override
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public BlockPosition getBlockPos() {
		return this.blockPos;
	}

	@Override
	public HandType getHandType() {
		return this.handType;
	}

	@Override
	public ItemStack getItemStack() {
		return this.itemStack;
	}

	@Override
	public void cancel() {
		this.evt0.setCancelled(true);
	}

}
