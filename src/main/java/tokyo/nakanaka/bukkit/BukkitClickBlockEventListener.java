package tokyo.nakanaka.bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Item;
import tokyo.nakanaka.Main;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.Scheduler;
import tokyo.nakanaka.World;
import tokyo.nakanaka.bukkit.commandSender.BukkitPlayer;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.event.ClickBlockEvent.HandType;

public class BukkitClickBlockEventListener implements Listener{
	private Main main;
	private Scheduler scheduler;
	private Map<UUID, Boolean> activateRightMapNew = new HashMap<>();
	
	public BukkitClickBlockEventListener(Main main, Scheduler scheduler) {
		this.main = main;
		this.scheduler = scheduler;
	}
	
	@EventHandler
	public void onLeftClickBlock(BlockBreakEvent evt0) {
		org.bukkit.entity.Player player0 = evt0.getPlayer();
		Item item = null;
		try {
			item = Item.valueOf(player0.getInventory().getItemInMainHand().getType().toString());
		}catch(IllegalArgumentException e) {
			return;
		}
		if(item != Item.BLAZE_ROD) {
			return;
		}
		evt0.setCancelled(true);
		Player player = new BukkitPlayer(player0);
		World world = new BukkitWorld(player0.getServer(), player0.getWorld());
		Location loc = evt0.getBlock().getLocation();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		BlockPosition blockPos = new BlockPosition(world, x, y, z);
		ClickBlockEvent evt = new ClickBlockEvent(player, blockPos, HandType.LEFT_HAND, item);
		this.main.onClickBlockEvent(evt);
	}
	
	@EventHandler
	public void onRightClickBlock(PlayerInteractEvent evt0) {
		if(evt0.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		org.bukkit.entity.Player player0 = evt0.getPlayer();
		Item item = null;
		try {
			item = Item.valueOf(player0.getInventory().getItemInMainHand().getType().toString());
		}catch(IllegalArgumentException e) {
			return;
		}
		if(item != Item.BLAZE_ROD) {
			return;
		}
		evt0.setCancelled(true);
		Player player = new BukkitPlayer(player0);
		Boolean canActivate = this.activateRightMapNew.get(player.getUniqueID());
		if(canActivate == null) {
			canActivate = true;
		}
		if(!canActivate) {
			return;
		}
		World world = new BukkitWorld(player0.getServer(), player0.getWorld());
		Location loc = evt0.getClickedBlock().getLocation();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		BlockPosition blockPos = new BlockPosition(world, x, y, z);
		ClickBlockEvent evt = new ClickBlockEvent(player, blockPos, HandType.RIGHT_HAND, item);
		this.main.onClickBlockEvent(evt);
		this.activateRightMapNew.put(player.getUniqueID(), false);
		this.scheduler.scheduleLater(1, () -> this.activateRightMapNew.put(player.getUniqueID(), true));
	}

}
