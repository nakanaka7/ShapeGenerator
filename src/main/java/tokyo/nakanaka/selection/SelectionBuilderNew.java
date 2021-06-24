package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.world.World;

public class SelectionBuilderNew {
	private World world;
	private RegionBuilder regionBuilder;
	private Vector3D offset;
	private static final String OFFSET = "offset";

	public SelectionBuilderNew(World world, RegionBuilder regionBuilder) {
		this.world = world;
		this.regionBuilder = regionBuilder;
	}

	public World getWorld() {
		return this.world;
	}

	boolean onLeftClickBlock(Logger logger, BlockVector3D blockPos) {
		return this.regionBuilder.onLeftClickBlock(logger, blockPos);
	}
	boolean onRightClickBlock(Logger logger, BlockVector3D blockPos) {
		return this.regionBuilder.onRightClickBlock(logger, blockPos);
	}
	
	boolean onCommand(Logger logger, BlockVector3D playerPos, String label, String[] args) {
		return this.regionBuilder.onCommand(logger, playerPos, label, args);
	}
	
	List<String> onTabComplete(String label, String[] args){
		if(label.equals(OFFSET)) {
			if(args.length <= 3) {
				return Arrays.asList("~");
			}else {
				return new ArrayList<>();
			}
		}else {
			return this.regionBuilder.onTabComplete(label, args);
		}
	}
	
	List<String> getStateLines(){
		List<String> list = new ArrayList<>(this.regionBuilder.getStateLines());
		String line = OFFSET + ": ";
		if(this.offset != null) {
			line += offset.toString();
		}
		list.add(line);
		return list;
	}
	/**
	 * @throw IllegalStateException
	 */
	Selection build() {
		return new Selection(this.world, this.regionBuilder.build(), this.offset);
	}
}
