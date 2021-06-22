package tokyo.nakanaka.bukkit;

import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import tokyo.nakanaka.PlayerEntity;
import tokyo.nakanaka.world.World;
@Deprecated
public class BukkitPlayerEntity implements PlayerEntity{
	private Server server;
	private Player player;
	
	public BukkitPlayerEntity(Server server, Player player) {
		this.server = server;
		this.player = player;
	}
	
	@Override
	public UUID getUniqueID() {
		return this.player.getUniqueId();
	}

	@Override
	public World getWorld() {
		return new BukkitWorld(this.server, this.player.getWorld());
	}

	@Override
	public int getX() {
		return this.player.getLocation().getBlockX();
	}

	@Override
	public int getY() {
		return this.player.getLocation().getBlockY();
	}

	@Override
	public int getZ() {
		return this.player.getLocation().getBlockZ();
	}

	@Override
	public void print(String msg) {
		this.player.sendMessage(msg);
	}

}
