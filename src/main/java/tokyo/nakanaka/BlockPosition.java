package tokyo.nakanaka;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.world.World;

public record BlockPosition(World world, int x, int y, int z) {
	public BlockPosition(World world, BlockVector3D pos) {
		this(world, pos.getX(), pos.getY(), pos.getZ());
	}
	
}
