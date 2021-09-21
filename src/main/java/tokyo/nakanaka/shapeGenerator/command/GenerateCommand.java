package tokyo.nakanaka.shapeGenerator.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.World;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.shapeGenerator.Selection;

/**
 * A command to generate blocks in the selection
 */
@PublicAPI
public class GenerateCommand implements UndoableCommand{
	private Selection sel;
	private Block block;
	private boolean physics;
	private Map<BlockVector3D, Block> originalBlockMap = new HashMap<>();
	private boolean havingUndone;
	
	/**
	 * Constructs the command
	 * @param sel a selection in which this command generates blocks
	 * @param block block to generate
	 * @param physics physics option to generate block
	 */
	@PublicAPI
	public GenerateCommand(Selection sel, Block block, boolean physics) {
		this.sel = sel;
		this.block = block;
		this.physics = physics;
	}
	
	@PrivateAPI
	public Selection getSelection() {
		return sel;
	}
	
	@PrivateAPI
	public Block getBlock() {
		return block;
	}

	@PrivateAPI
	public boolean hasUndone() {
		return havingUndone;
	}

	/**
	 *@throws IllegalArgumentException if the world cannot generate the block
	 */
	@Override
	@PublicAPI
	public void execute() {
		World world = this.sel.world();
		Set<BlockVector3D> vecSet = this.sel.createBlockRegion3D().asVectorSet();
		for(BlockVector3D v : vecSet) {
			Block block = world.getBlock(v.getX(), v.getY(), v.getZ());
			world.setBlock(v.getX(), v.getY(), v.getZ(), this.block, this.physics);
			this.originalBlockMap.put(v, block);
		}
	}

	@Override
	@PublicAPI
	public void undo() {
		World world = this.sel.world();
		for(Entry<BlockVector3D, Block> e : this.originalBlockMap.entrySet()) {
			BlockVector3D pos = e.getKey();
			world.setBlock(pos.getX(), pos.getY(), pos.getZ(), e.getValue(), this.physics);
		}
		this.havingUndone = true;
	}

	@Override
	@PublicAPI
	public void redo() {
		World world = this.sel.world();
		Set<BlockVector3D> vecSet = this.sel.createBlockRegion3D().asVectorSet();
		for(BlockVector3D v : vecSet) {
			world.setBlock(v.getX(), v.getY(), v.getZ(), this.block, this.physics);
		}
		this.havingUndone = false;
	}

}
