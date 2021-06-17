package tokyo.nakanaka.command;

import java.util.Set;

import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.shapeGenerator.BlockRegion3D;
import tokyo.nakanaka.world.World;

public class FillCommand implements UndoableCommand{
	private World world;
	private BlockRegion3D region;
	private Block block;
	private boolean physics;
	
	public FillCommand(Builder builder) {
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
		
		public FillCommand build() {
			return new FillCommand(this);
		}

	}
	
	/**
	 *@throws IllegalArgumentException
	 */
	@Override
	public void execute() {
		Set<BlockVector3D> vecSet = this.region.asVectorSet();
		for(BlockVector3D v : vecSet) {
			this.world.setBlock(v.getX(), v.getY(), v.getZ(), this.block, this.physics);
		}
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

}
