package tokyo.nakanaka.bukkit.commandSender;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.World;
import tokyo.nakanaka.bukkit.BukkitWorld;
import tokyo.nakanaka.commandSender.BlockCommandSender;

public class BukkitBlockCommandSender extends BukkitCommandSender implements BlockCommandSender { 
	
	public BukkitBlockCommandSender(org.bukkit.command.BlockCommandSender blockCmdSender) {
		super(blockCmdSender);
	}

	@Override
	public BlockPosition getBlockPosition() {
		org.bukkit.command.BlockCommandSender b = (org.bukkit.command.BlockCommandSender)this.sender;
		org.bukkit.block.Block block = b.getBlock();
		World world = new BukkitWorld(b.getServer(), block.getWorld());
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		return new BlockPosition(world, x, y, z);
	}

}
