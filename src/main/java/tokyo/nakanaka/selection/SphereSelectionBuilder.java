package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commandArgument.LengthArgument;
import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.Region3Ds;
import tokyo.nakanaka.math.region3D.SphereRegion3D;
import tokyo.nakanaka.world.World;

public class SphereSelectionBuilder implements SelectionBuilder{
	private World world;
	private Vector3D center;
	private double radius;
	private Vector3D offset;
	private static final String CENTER = "center";
	private static final String RADIUS = "radius";
	private static final String OFFSET = "offset";
	private PositionArgument centerArg = new PositionArgument();
	private LengthArgument radiusArg = new LengthArgument();
	private PositionArgument offsetArg = new PositionArgument();
	
	public SphereSelectionBuilder(World world) {
		this.world = world;
	}
	
	@Override
	public World getWorld() {
		return this.world;
	}
	
	@Override
	public boolean onLeftClickBlock(int x, int y, int z) {
		this.center = new Vector3D(x, y, z);
		return true;
	}

	@Override
	public boolean onRightClickBlock(int x, int y, int z) {
		Vector3D pos = new Vector3D(x, y, z);
		if(this.center == null) {
			return false;
		}
		double radius = Math.floor(pos.negate(this.center).getAbsolute()) + 0.5;
		this.radius = radius;
		return true;
	}

	@Override
	public boolean onCommand(int posX, int posY, int posZ, String[] args) {
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
		}else if(label.equals(RADIUS)) {
			if(shiftArgs.length != 1) {
				return false;
			}
			double r;
			try {
				r = this.radiusArg.onParse(shiftArgs[0]);
			}catch(IllegalArgumentException e) {
				return false;
			}	
			this.radius = r;
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
	public List<String> onTabComplete(String[] args) {
		if(args.length == 0) {
			return new ArrayList<>();
		}
		if(args.length == 1) {
			return Arrays.asList(CENTER, RADIUS, OFFSET);
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		if(label.equals(CENTER)){
			return this.centerArg.onTabComplete(shiftArgs);
		}else if(label.equals(RADIUS)) {
			return this.radiusArg.onTabComplete(shiftArgs[0]);
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
		String line2 = RADIUS + ": " + this.radius;
		String line3 = OFFSET + ": ";
		if(offset != null) {
			line3 += offset.toString();
		}else {
			line3 += "= center";
		}
		return Arrays.asList(line1, line2, line3);
	}

	@Override
	public Selection build() {
		if(this.world == null) {
			throw new IllegalStateException();
		}
		Region3D region = new SphereRegion3D(this.radius);
		if(this.center == null) {
			throw new IllegalStateException();
		}
		region = Region3Ds.shift(region, this.center);
		double ubx = center.getX() + radius;
		double uby = center.getY() + radius;
		double ubz = center.getZ() + radius;
		double lbx = center.getX() - radius;
		double lby = center.getY() - radius;
		double lbz = center.getZ() - radius;
		BoundRegion3D boundRegion = new BoundRegion3D(region, ubx, uby, ubz, lbx, lby, lbz);
		if(this.offset == null) {
			this.offset = center;
		}
		return new Selection(this.world, boundRegion, this.offset);
	}

}
