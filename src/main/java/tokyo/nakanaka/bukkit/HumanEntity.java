package tokyo.nakanaka.bukkit;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Server;

import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.CuboidSelectionBuilder;
import tokyo.nakanaka.world.World;

public class HumanEntity {
	private Server server;
	private org.bukkit.entity.Player bukkitPlayer;
	private UUID uid;
	
	public HumanEntity(Server server, org.bukkit.entity.Player bukkitPlayer) {
		this.server = server;
		this.bukkitPlayer = bukkitPlayer;
		this.uid = bukkitPlayer.getUniqueId();
	}
	
	public UUID getUniqueID() {
		return this.uid;
	}
	
	public World getWorld() {
		return new BukkitWorld(this.server, this.bukkitPlayer.getWorld());
	}
	
	public Player toNewPlayer() {
		Player player = new Player(this.uid);
		Location loc = this.bukkitPlayer.getLocation();
		World world = new BukkitWorld(this.server, loc.getWorld());
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		player.setWorld(world);
		player.setX(x);
		player.setY(y);
		player.setZ(z);
		player.setLogger(new BukkitLogger(this.bukkitPlayer));
		player.setSelectionBuilder(new CuboidSelectionBuilder(world));
		return player;
	}
	
}
