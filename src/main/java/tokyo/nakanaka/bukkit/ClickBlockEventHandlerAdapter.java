package tokyo.nakanaka.bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.cuboid.CuboidSelectionStrategy;
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
		if(type != Material.BLAZE_ROD) {
			return;
		}
		e.setCancelled(true);
		UUID uid = bukkitPlayer.getUniqueId();
		Player player = this.playerRepo.getHumanPlayer(uid);
		Location loc = e.getBlock().getLocation();
		World world = new BukkitWorld(this.server, loc.getWorld());
		if(player == null) {
			player = new Player(uid);
			player.setSelectionShape(SelectionShape.CUBOID);
			RegionBuildingData regionData = new CuboidSelectionStrategy().newRegionBuildingData();
			SelectionBuildingData selData = new SelectionBuildingData(world, regionData);
			player.setSelectionBuildingData(selData);
			this.playerRepo.registerHumanPlayer(player);
		}
		player.setLogger(new BukkitLogger(bukkitPlayer));
		player.setWorld(world);
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		this.clickHandler.onLeftClickBlock(player, x, y, z);
	}
	
	@EventHandler
	public void onRightClickBlock(PlayerInteractEvent e) {
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		org.bukkit.entity.Player bukkitPlayer = e.getPlayer();
		ItemStack itemStack = bukkitPlayer.getInventory().getItemInMainHand();
		Material type = itemStack.getType();
		if(type != Material.BLAZE_ROD) {
			return;
		}
		e.setCancelled(true);
		UUID uid = bukkitPlayer.getUniqueId();
		Player player = this.playerRepo.getHumanPlayer(bukkitPlayer.getUniqueId());
		Location loc = e.getClickedBlock().getLocation();
		World world = new BukkitWorld(this.server, loc.getWorld());
		if(player == null) {
			player = new Player(uid);
			player.setSelectionShape(SelectionShape.CUBOID);
			RegionBuildingData regionData = new CuboidSelectionStrategy().newRegionBuildingData();
			SelectionBuildingData selData = new SelectionBuildingData(world, regionData);
			player.setSelectionBuildingData(selData);		
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
		player.setWorld(world);
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		this.clickHandler.onRightClickBlock(player, x, y, z);
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
