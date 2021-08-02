package tokyo.nakanaka.bukkit.commandSender;

import java.util.UUID;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.bukkit.BukkitWorld;
import tokyo.nakanaka.commandSender.Player;
import tokyo.nakanaka.world.World;

public class BukkitPlayer extends BukkitCommandSender implements Player {
	public BukkitPlayer(org.bukkit.entity.Player player0) {
		super(player0);
	}

	@Override
	public BlockPosition getBlockPosition() {
		org.bukkit.entity.Player player0 = (org.bukkit.entity.Player)this.sender;
		World world = new BukkitWorld(player0.getServer(), player0.getWorld());
		int x = player0.getLocation().getBlockX();
		int y = player0.getLocation().getBlockY();
		int z = player0.getLocation().getBlockZ();
		return new BlockPosition(world, x, y, z);
	}

	@Override
	public UUID getUniqueID() {
		org.bukkit.entity.Player player0 = (org.bukkit.entity.Player)this.sender;
		return player0.getUniqueId();
	}

}
