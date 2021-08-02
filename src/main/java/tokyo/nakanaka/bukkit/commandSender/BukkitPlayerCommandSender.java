package tokyo.nakanaka.bukkit.commandSender;

import java.util.UUID;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.bukkit.BukkitWorld;
import tokyo.nakanaka.commandSender.PlayerCommandSender;
import tokyo.nakanaka.world.World;

public class BukkitPlayerCommandSender implements PlayerCommandSender {
	private org.bukkit.entity.Player player;
	
	public BukkitPlayerCommandSender(org.bukkit.entity.Player player0) {
		this.player = player0;
	}

	@Override
	public void print(String msg) {
		this.player.sendMessage(msg);
	}

	@Override
	public BlockPosition getBlockPosition() {
		World world = new BukkitWorld(this.player.getServer(), this.player.getWorld());
		int x = this.player.getLocation().getBlockX();
		int y = this.player.getLocation().getBlockY();
		int z = this.player.getLocation().getBlockZ();
		return new BlockPosition(world, x, y, z);
	}

	@Override
	public UUID getUniqueID() {
		return player.getUniqueId();
	}

}
