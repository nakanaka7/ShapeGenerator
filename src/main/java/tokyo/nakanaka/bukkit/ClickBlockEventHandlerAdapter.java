package tokyo.nakanaka.bukkit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import tokyo.nakanaka.ClickBlockEventHandler;
import tokyo.nakanaka.Scheduler;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.player.PlayerRepository;
import tokyo.nakanaka.world.World;

public class ClickBlockEventHandlerAdapter implements Listener{
	private Server server;
	private PlayerRepository playerRepo;
	private Scheduler scheduler;
	private ClickBlockEventHandler clickHandler;
	private Map<Player, Boolean> activateRightMap = new HashMap<>();
	
	public ClickBlockEventHandlerAdapter(Server server, PlayerRepository playerRepo, Scheduler scheduler, ClickBlockEventHandler clickHandler) {
		this.server = server;
		this.playerRepo = playerRepo;
		this.scheduler = scheduler;
		this.clickHandler = clickHandler;
	}

	@EventHandler
	public void onLeftClickBlock(BlockBreakEvent e) {
		org.bukkit.entity.Player bukkitPlayer = e.getPlayer();
		ItemStack itemStack = bukkitPlayer.getInventory().getItemInMainHand();
		Material type = itemStack.getType();
		if(type == Material.BLAZE_ROD) {
			e.setCancelled(true);
		}
		Player player = this.playerRepo.getHumanPlayer(bukkitPlayer.getUniqueId());
		if(player == null) {
			player = new Player(bukkitPlayer.getUniqueId(), bukkitPlayer.getName());
			this.playerRepo.registerHumanPlayer(player);
		}
		player.setLogger(new BukkitLogger(bukkitPlayer));
		Location loc = e.getBlock().getLocation();
		org.bukkit.World bukkitWorld = loc.getWorld();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		World world = new BukkitWorld(this.server, bukkitWorld);
		this.clickHandler.onLeftClickBlock(player, world, x, y, z);
	}
	
	@EventHandler
	public void onRightClickBlock(PlayerInteractEvent e) {
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		org.bukkit.entity.Player bukkitPlayer = e.getPlayer();
		ItemStack itemStack = bukkitPlayer.getInventory().getItemInMainHand();
		Material type = itemStack.getType();
		if(type == Material.BLAZE_ROD) {
			e.setCancelled(true);
		}
		Player player = this.playerRepo.getHumanPlayer(bukkitPlayer.getUniqueId());
		if(player == null) {
			player = new Player(bukkitPlayer.getUniqueId(), bukkitPlayer.getName());
			this.playerRepo.registerHumanPlayer(player);
		}
		player.setLogger(new BukkitLogger(bukkitPlayer));
		Boolean canActivate = this.activateRightMap.get(player);
		if(canActivate == null) {
			canActivate = true;
		}
		if(!canActivate) {
			return;
		}
		Location loc = e.getClickedBlock().getLocation();
		org.bukkit.World bukkitWorld = loc.getWorld();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		World world = new BukkitWorld(this.server, bukkitWorld);
		this.clickHandler.onRightClickBlock(player, world, x, y, z);
		this.activateRightMap.put(player, false);
		Player finalPlayer = player;//final or effectively final
		Runnable activate = new Runnable() {
			@Override
			public void run() {
				activateRightMap.put(finalPlayer, true);
			}
		};
		this.scheduler.scheduleLater(1, activate);
	}
	
}
