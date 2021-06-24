package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.CuboidRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.world.World;

public class CuboidSelectionBuilder implements SelectionBuilder{
	private World world;
	private Vector3D pos1;
	private Vector3D pos2;
	private Vector3D offset;
	private static final String POS1 = "pos1";
	private static final String POS2 = "pos2";
	private static final String OFFSET = "offset";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String LENGTH = "length";
	private PositionArgument pos1Arg = new PositionArgument();
	private PositionArgument pos2Arg = new PositionArgument();
	private PositionArgument offsetArg = new PositionArgument();
	
	public CuboidSelectionBuilder(World world) {
		this.world = world;
	}

	@Override
	public World getWorld() {
		return this.world;
	}
	
	@Override
	public boolean onLeftClickBlock(Logger logger, BlockVector3D blockPos) {
		this.pos1 = new Vector3D(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		return true;
	}

	@Override
	public boolean onRightClickBlock(Logger logger, BlockVector3D blockPos) {
		this.pos2 = new Vector3D(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		return true;
	}
	
	@Override
	public boolean onCommand(Logger logger, BlockVector3D playerPos, String[] args) {
		if(args.length == 0) {
			return false;
		}
		int posX = playerPos.getX();
		int posY = playerPos.getY();
		int posZ = playerPos.getZ();
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		if(label.equals(OFFSET)){
			Vector3D offset;
			try {
				offset = this.offsetArg.onParse(new BlockVector3D(posX, posY, posZ), shiftArgs);
			}catch(IllegalArgumentException e) {
				return false;
			}
			this.offset = offset;
			return true;
		}else if(args[0].equals(POS1)) {
			Vector3D pos1;
			try {
				pos1 = this.pos1Arg.onParse(new BlockVector3D(posX, posY, posZ), shiftArgs);
			}catch(IllegalArgumentException e) {
				return false;
			}
			this.pos1 = pos1;
			return true;
		}else if(args[0].equals(POS2)) {
			Vector3D pos2;
			try {
				pos2 = this.pos2Arg.onParse(new BlockVector3D(posX, posY, posZ), shiftArgs);
			}catch(IllegalArgumentException e) {
				return false;
			}
			this.pos2 = pos2;
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<String> onLabelList() {
		return Arrays.asList(POS1, POS2, OFFSET);
	}
	
	@Override
	public List<String> onTabComplete(String[] args) {
		if(args.length == 0) {
			return new ArrayList<>();
		}
		if(args.length == 1) {
			return Arrays.asList(POS1, POS2, OFFSET);
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		if(label.equals(POS1)){
			return this.pos1Arg.onTabComplete(shiftArgs);
		}else if(label.equals(POS2)) {
			return this.pos2Arg.onTabComplete(shiftArgs);
		}else if(label.equals(OFFSET)) {
			return this.offsetArg.onTabComplete(shiftArgs);
		}else {
			return new ArrayList<>();
		}
	}
	
	@Override
	public List<String> getStateLines() {
		String line1 = POS1 + ": ";
		if(this.pos1 != null) {
			line1 += pos1.toString();
		}
		String line2 = POS2 + ": ";
		if(this.pos2 != null) {
			line2 += pos2.toString();
		}
		String line3 = OFFSET + ": ";
		if(offset != null) {
			line3 += offset.toString();
		}
		String line4 = WIDTH + ": ";
		String line5 = HEIGHT + ": ";
		String line6 = LENGTH + ": ";
		if(pos1 != null && pos2 != null) {
			double width = pos2.getX() - pos1.getX();
			double height = pos2.getY() - pos1.getY();
			double length = pos2.getZ() - pos1.getZ();
			line4 += String.valueOf(width);
			line5 += String.valueOf(height);
			line6 += String.valueOf(length);
		}
		return Arrays.asList(line1, line2, line3, line4, line5, line6);
	}
	
	@Override
	public Selection build() {
		if(this.world == null || this.offset == null) {
			throw new IllegalStateException();
		}
		Region3D region = new CuboidRegion3D(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
		double ubx = Math.max(pos1.getX(), pos2.getX());
		double uby = Math.max(pos1.getY(), pos2.getY());
		double ubz = Math.max(pos1.getZ(), pos2.getZ());
		double lbx = Math.min(pos1.getX(), pos2.getX());
		double lby = Math.min(pos1.getY(), pos2.getY());
		double lbz = Math.min(pos1.getZ(), pos2.getZ());
		BoundRegion3D boundRegion = new BoundRegion3D(region, ubx, uby, ubz, lbx, lby, lbz);
		return new Selection(this.world, boundRegion, this.offset);
	}

}
