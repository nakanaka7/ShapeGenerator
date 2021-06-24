package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commandArgument.LengthArgument;
import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.Region3Ds;
import tokyo.nakanaka.math.region3D.TorusRegion3D;
import tokyo.nakanaka.world.World;

public class TorusSelectionBuilder implements SelectionBuilder{
	private World world;
	private Vector3D center;
	private double radiusMain;
	private double radiusSub;
	private Vector3D offset;
	private static final String CENTER = "center";
	private static final String RADIUS_MAIN = "radius_main";
	private static final String RADIUS_SUB = "radius_sub";
	private static final String OFFSET = "offset";
	private PositionArgument centerArg = new PositionArgument();
	private LengthArgument radiusMainArg = new LengthArgument();
	private LengthArgument radiusSubArg = new LengthArgument();
	private PositionArgument offsetArg = new PositionArgument();
	
	public TorusSelectionBuilder(World world) {
		this.world = world;
	}

	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public boolean onLeftClickBlock(Logger logger, BlockVector3D blockPos) {
		int x = blockPos.getX();
		int y = blockPos.getY();
		int z = blockPos.getZ();
		this.center = new Vector3D(x, y, z);
		return true;
	}

	@Override
	public boolean onRightClickBlock(Logger logger, BlockVector3D blockPos) {
		return false;
	}

	@Override
	public boolean onCommand(Logger logger, BlockVector3D playerPos, String[] args) {
		int posX = playerPos.getX();
		int posY = playerPos.getY();
		int posZ = playerPos.getZ();
		if(args.length == 0) {
			return false;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		if(label.equals(CENTER)) {
			Vector3D center;
			try {
				center = this.centerArg.onParse(new BlockVector3D(posX, posY, posZ), shiftArgs);
			}catch(IllegalArgumentException e) {
				return false;
			}
			this.center = center;
			return true;
		}else if(label.equals(RADIUS_MAIN)) {
			if(shiftArgs.length != 1) {
				return false;
			}
			double radiusMain;
			try {
				radiusMain = this.radiusMainArg.onParse(shiftArgs[0]);
			}catch(IllegalArgumentException e) {
				return false;
			}	
			this.radiusMain = radiusMain;
			return true;
		}else if(label.equals(RADIUS_SUB)) {
			if(shiftArgs.length != 1) {
				return false;
			}
			double radiusSub;
			try {
				radiusSub = this.radiusSubArg.onParse(shiftArgs[0]);
			}catch(IllegalArgumentException e) {
				return false;
			}	
			this.radiusSub = radiusSub;
			return true;
		}else if(label.equals(OFFSET)){
			Vector3D offset;
			try {
				offset = this.offsetArg.onParse(new BlockVector3D(posX, posY, posZ), shiftArgs);
			}catch(IllegalArgumentException e) {
				return false;
			}
			this.offset = offset;
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public List<String> onLabelList() {
		return Arrays.asList(CENTER, RADIUS_MAIN, RADIUS_SUB, OFFSET);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		if(args.length == 0) {
			return new ArrayList<>();
		}
		if(args.length == 1) {
			return Arrays.asList(CENTER, RADIUS_MAIN, RADIUS_SUB, OFFSET);
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		if(label.equals(CENTER)){
			return this.centerArg.onTabComplete(shiftArgs);
		}else if(label.equals(RADIUS_MAIN)) {
			return this.radiusMainArg.onTabComplete(shiftArgs[0]);
		}else if(label.equals(RADIUS_SUB)) {
			return this.radiusSubArg.onTabComplete(shiftArgs[0]);
		}else if(label.equals(OFFSET)) {
			return this.offsetArg.onTabComplete(shiftArgs);
		}else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<String> getStateLines() {
		String line1 = CENTER + ": ";
		if(this.center != null) {
			line1 += center.toString();
		}
		String line2 = RADIUS_MAIN + ": " + this.radiusMain;
		String line3 = RADIUS_SUB + ": " + this.radiusSub;
		String line4 = OFFSET + ": ";
		if(offset != null) {
			line4 += offset.toString();
		}else {
			line4 += "= center";
		}
		return Arrays.asList(line1, line2, line3, line4);
	}

	@Override
	public Selection build() {
		Region3D region = new TorusRegion3D(this.radiusMain, this.radiusSub);
		if(this.center == null) {
			throw new IllegalStateException();
		}
		region = Region3Ds.shift(region, this.center);
		double ubx = center.getX() + radiusMain + radiusSub;
		double uby = center.getY() + radiusMain + radiusSub;
		double ubz = center.getZ() + radiusMain + radiusSub;
		double lbx = center.getX() - radiusMain - radiusSub;
		double lby = center.getY() - radiusMain - radiusSub;
		double lbz = center.getZ() - radiusMain - radiusSub;
		BoundRegion3D boundRegion = new BoundRegion3D(region, ubx, uby, ubz, lbx, lby, lbz);
		if(this.offset == null) {
			this.offset = center;
		}
		return new Selection(this.world, boundRegion, this.offset);
	}

}
