package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.List;

import static tokyo.nakanaka.logger.LogConstant.*;
import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.world.World;

public abstract class AbstractSelectionBuilder implements SelectionBuilder{
	private World world;
	protected RegionBuilder builder;
	private Vector3D offset;
	private static final String OFFSET = "offset";
	private PositionArgument offsetArg = new PositionArgument();
	
	public AbstractSelectionBuilder(World world, RegionBuilder builder) {
		this.world = world;
		this.builder = builder;
	}
	
	public World getWorld() {
		return world;
	}

	public void onLeftClickBlock(Logger logger, BlockVector3D blockPos) {
		this.builder.onLeftClickBlock(logger, blockPos);
	}
	
	public void onRightClickBlock(Logger logger, BlockVector3D blockPos) {
		this.builder.onRightClickBlock(logger, blockPos);
	}
	
	public boolean onCommand(Logger logger, BlockVector3D playerPos, String label, String[] args) {
		if(label.equals(OFFSET)){
			Vector3D offset;
			try {
				offset = this.offsetArg.onParse(playerPos, args);
			}catch(IllegalArgumentException e) {
				logger.print(HEAD_ERROR + "Can not parse coordinates");
				return true;
			}
			this.offset = offset;
			return true;
		}
		return this.builder.onCommand(logger, playerPos, label, args);
	}
	
	public List<String> onLabelList(){
		List<String> list = new ArrayList<>(this.builder.onLabelList());
		list.add(OFFSET);
		return list;
	}
	
	public List<String> onTabComplete(String label, String[] args){
		if(label.equals(OFFSET)) {
			return this.offsetArg.onTabComplete(args);
		}else {
			return this.builder.onTabComplete(label, args);
		}
	}
	
	public List<String> getStateLines(){
		List<String> list = new ArrayList<>(this.builder.getStateLines());
		String lineOffset = OFFSET + ": ";
		if(offset == null) {
			lineOffset += this.getDefaultOffsetName();
		}else {
			lineOffset += this.offset.toString();
		}
		list.add(lineOffset);
		return list;
	}
	
	abstract String getDefaultOffsetName();
	abstract Vector3D getDefaultOffset();
	
	/**
	 * @throw IllegalStateException
	 */
	public Selection build() {
		BoundRegion3D boundRegion = this.builder.build();
		Vector3D offset = this.offset;
		if(offset == null) {
			offset = this.getDefaultOffset();
		}
		return new Selection(this.world, boundRegion, offset);
	}
}
