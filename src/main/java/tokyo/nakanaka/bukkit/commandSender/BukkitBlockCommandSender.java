package tokyo.nakanaka.bukkit.commandSender;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.bukkit.BukkitWorld;
import tokyo.nakanaka.commandSender.BlockCommandSender;
import tokyo.nakanaka.world.World;

public class BukkitBlockCommandSender implements BlockCommandSender { 
	private org.bukkit.command.BlockCommandSender blockCmdSender;
	
	public BukkitBlockCommandSender(org.bukkit.command.BlockCommandSender blockCmdSender) {
		this.blockCmdSender = blockCmdSender;
	}

	@Override
	public BlockPosition getBlockPosition() {
		org.bukkit.block.Block block = this.blockCmdSender.getBlock();
		World world = new BukkitWorld(this.blockCmdSender.getServer(), block.getWorld());
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		return new BlockPosition(world, x, y, z);
	}

	@Override
	public void print(String msg) {
		blockCmdSender.sendMessage(msg);
	}

}
