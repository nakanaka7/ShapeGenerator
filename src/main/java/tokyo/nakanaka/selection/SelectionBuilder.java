package tokyo.nakanaka.selection;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.world.World;

public abstract class SelectionBuilder {
	private World world;
	private Vector3D offset;
	private static final String OFFSET = "offset";
	private PositionArgument offsetArg = new PositionArgument();
	
	public SelectionBuilder(World world) {
		this.world = world;
	}
		
	public abstract void onLeftClickBlock(Logger logger, BlockVector3D blockPos);
	
	public abstract void onRightClickBlock(Logger logger, BlockVector3D blockPos);
	
	public abstract boolean onRegionCommand(Logger logger, BlockVector3D playerPos, String label, String[] args);
	
	public abstract List<String> getRegionCommandLabelList();
	
	public abstract List<String> onRegionCommandTabComplete(String label, String[] args);
	
	public abstract List<String> getRegionStateLines();
	
	public abstract Vector3D getDefaultOffset();
	
	public abstract String getDefaultOffsetName();
	
	/**
	 * @throw IllegalStateException
	 */
	public abstract BoundRegion3D buildRegion();
	
	public World getWorld() {
		return world;
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
		return this.onRegionCommand(logger, playerPos, label, args);
	}
	
	public List<String> getLabelList(){
		List<String> list = new ArrayList<>(this.getRegionCommandLabelList());
		list.add(OFFSET);
		return list;
	}
	
	public List<String> onTabComplete(String label, String[] args){
		if(label.equals(OFFSET)) {
			return this.offsetArg.onTabComplete(args);
		}else {
			return this.onRegionCommandTabComplete(label, args);
		}
	}
	
	public List<String> getStateLines(){
		List<String> list = new ArrayList<>(this.getRegionStateLines());
		String lineOffset = OFFSET + ": ";
		if(offset == null) {
			lineOffset += this.getDefaultOffsetName();
		}else {
			lineOffset += this.offset.toString();
		}
		list.add(lineOffset);
		return list;
	}
	
	
	/**
	 * @throw IllegalStateException
	 */
	public Selection build() {
		BoundRegion3D boundRegion = this.buildRegion();
		Vector3D offset = this.offset;
		if(offset == null) {
			offset = this.getDefaultOffset();
		}
		return new Selection(this.world, boundRegion, offset);
	}
}
