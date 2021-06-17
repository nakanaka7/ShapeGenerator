package tokyo.nakanaka.bukkit;

import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;

import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.world.World;

public class BukkitWorld implements World{
	private Server server;
	private org.bukkit.World world;
	private UUID uid;
	
	public BukkitWorld(Server server, org.bukkit.World world) {
		this.server = server;
		this.world = world;
		this.uid = world.getUID();
	}
	
	@Override
	public Block getBlock(int x, int y, int z) {
		org.bukkit.block.Block bukkitBlock = world.getBlockAt(x, y, z);
		Block block = Block.valueOf(bukkitBlock.getBlockData().getAsString());
		return block;
	}
	
	@Override
	public void setBlock(int x, int y, int z, Block block, boolean physics) {
		BlockData bd = server.createBlockData(block.getBlockStateString());
		org.bukkit.block.Block bukkitBlock = world.getBlockAt(x, y, z);
		bukkitBlock.setBlockData(bd, physics);
		BlockState state = bukkitBlock.getState();
		state.update();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BukkitWorld other = (BukkitWorld) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}
	
}
