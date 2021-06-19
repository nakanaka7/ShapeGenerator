package tokyo.nakanaka.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import tokyo.nakanaka.BlockRegion3D;
import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.world.World;

public class GenerateCommand implements UndoableCommand{
	private World world;
	private BlockRegion3D region;
	private Block block;
	private boolean physics;
	private Map<BlockVector3D, Block> originalBlockMap = new HashMap<>();
	
	public GenerateCommand(Builder builder) {
		this.world = builder.world;
		this.region = builder.region;
		this.block = builder.block;
		this.physics = builder.physics;
	}

	public static class Builder{
		private World world;
		private BlockRegion3D region;
		private Block block;
		private boolean physics = false;
		
		public Builder(World world, BlockRegion3D region, Block block) {
			this.world = world;
			this.region = region;
			this.block = block;
		}

		public Builder physics(boolean physics) {
			this.physics = physics;
			return this;
		}
		
		public GenerateCommand build() {
			return new GenerateCommand(this);
		}

	}
	
	public Block getBlock() {
		return block;
	}

	/**
	 *@throws IllegalArgumentException
	 */
	@Override
	public void execute() {
		Set<BlockVector3D> vecSet = this.region.asVectorSet();
		for(BlockVector3D v : vecSet) {
			Block block = this.world.getBlock(v.getX(), v.getY(), v.getZ());
			this.world.setBlock(v.getX(), v.getY(), v.getZ(), this.block, this.physics);
			this.originalBlockMap.put(v, block);
		}
	}

	@Override
	public void undo() {
		for(Entry<BlockVector3D, Block> e : this.originalBlockMap.entrySet()) {
			BlockVector3D pos = e.getKey();
			this.world.setBlock(pos.getX(), pos.getY(), pos.getZ(), e.getValue(), this.physics);
		}
	}

	@Override
	public void redo() {
		Set<BlockVector3D> vecSet = this.region.asVectorSet();
		for(BlockVector3D v : vecSet) {
			this.world.setBlock(v.getX(), v.getY(), v.getZ(), this.block, this.physics);
		}
	}

}
