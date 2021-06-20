package tokyo.nakanaka.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.Selection;
import tokyo.nakanaka.world.World;

public class GenerateCommand implements UndoableCommand{
	private Selection sel;
	private Block block;
	private boolean physics;
	private Map<BlockVector3D, Block> originalBlockMap = new HashMap<>();
	
	public GenerateCommand(Selection sel, Block block, boolean physics) {
		this.sel = sel;
		this.block = block;
		this.physics = physics;
	}
	
	public Block getBlock() {
		return block;
	}

	/**
	 *@throws IllegalArgumentException
	 */
	@Override
	public void execute() {
		World world = this.sel.getWorld();
		Set<BlockVector3D> vecSet = this.sel.getBlockRegion3D().asVectorSet();
		for(BlockVector3D v : vecSet) {
			Block block = world.getBlock(v.getX(), v.getY(), v.getZ());
			world.setBlock(v.getX(), v.getY(), v.getZ(), this.block, this.physics);
			this.originalBlockMap.put(v, block);
		}
	}

	@Override
	public void undo() {
		World world = this.sel.getWorld();
		for(Entry<BlockVector3D, Block> e : this.originalBlockMap.entrySet()) {
			BlockVector3D pos = e.getKey();
			world.setBlock(pos.getX(), pos.getY(), pos.getZ(), e.getValue(), this.physics);
		}
	}

	@Override
	public void redo() {
		World world = this.sel.getWorld();
		Set<BlockVector3D> vecSet = this.sel.getBlockRegion3D().asVectorSet();
		for(BlockVector3D v : vecSet) {
			world.setBlock(v.getX(), v.getY(), v.getZ(), this.block, this.physics);
		}
	}

}
