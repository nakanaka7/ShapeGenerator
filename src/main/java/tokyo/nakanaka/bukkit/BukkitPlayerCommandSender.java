package tokyo.nakanaka.bukkit;

import java.util.UUID;

import org.bukkit.Server;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.commandSender.PlayerCommandSender;
import tokyo.nakanaka.world.World;

public class BukkitPlayerCommandSender implements PlayerCommandSender {
	private Server server;
	private org.bukkit.entity.Player player;
	
	public BukkitPlayerCommandSender(org.bukkit.entity.Player player) {
		this.player = player;
	}

	@Override
	public void print(String msg) {
		player.sendMessage(msg);
	}

	@Override
	public BlockPosition getBlockPosition() {
		World world = new BukkitWorld(server, player.getWorld());
		int x = player.getLocation().getBlockX();
		int y = player.getLocation().getBlockY();
		int z = player.getLocation().getBlockZ();
		return new BlockPosition(world, x, y, z);
	}

	@Override
	public UUID getUniqueID() {
		return player.getUniqueId();
	}

}
