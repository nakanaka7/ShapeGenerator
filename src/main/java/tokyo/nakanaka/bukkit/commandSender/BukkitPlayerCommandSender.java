package tokyo.nakanaka.bukkit.commandSender;

import java.util.UUID;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.bukkit.BukkitWorld;
import tokyo.nakanaka.commandSender.PlayerCommandSender;
import tokyo.nakanaka.world.World;

public class BukkitPlayerCommandSender implements PlayerCommandSender {
	private org.bukkit.entity.Player player0;
	
	public BukkitPlayerCommandSender(org.bukkit.entity.Player player0) {
		this.player0 = player0;
	}

	@Override
	public void print(String msg) {
		this.player0.sendMessage(msg);
	}

	@Override
	public BlockPosition getBlockPosition() {
		World world = new BukkitWorld(this.player0.getServer(), this.player0.getWorld());
		int x = this.player0.getLocation().getBlockX();
		int y = this.player0.getLocation().getBlockY();
		int z = this.player0.getLocation().getBlockZ();
		return new BlockPosition(world, x, y, z);
	}

	@Override
	public UUID getUniqueID() {
		return player0.getUniqueId();
	}

}
