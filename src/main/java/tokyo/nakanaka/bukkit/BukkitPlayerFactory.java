package tokyo.nakanaka.bukkit;

import org.bukkit.Location;
import org.bukkit.Server;

import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.cuboid.CuboidSelectionBuilder;
import tokyo.nakanaka.world.World;

public class BukkitPlayerFactory {
	
	public Player createHumanPlayer(Server server, org.bukkit.entity.Player bukkitPlayer) {
		Player player = new Player(bukkitPlayer.getUniqueId());
		Location loc = bukkitPlayer.getLocation();
		World world = new BukkitWorld(server, loc.getWorld());
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		player.setWorld(world);
		player.setX(x);
		player.setY(y);
		player.setZ(z);
		player.setLogger(new BukkitLogger(bukkitPlayer));
		player.setSelectionBuilder(new CuboidSelectionBuilder(world));
		return player;
	}
	
}
