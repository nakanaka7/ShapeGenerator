package tokyo.nakanaka.world;

import tokyo.nakanaka.block.Block;

public interface World {
	Block getBlock(int x, int y, int z);
	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param block
	 * @param physics
	 * @throws IllegalArgumentException
	 */
	void setBlock(int x, int y, int z, Block block, boolean physics);
}
