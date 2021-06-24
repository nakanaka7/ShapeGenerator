package tokyo.nakanaka.selection;

import static tokyo.nakanaka.selection.SelectionUtils.toVector3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.Region3Ds;
import tokyo.nakanaka.math.region3D.SphereRegion3D;
import tokyo.nakanaka.world.World;

public class SphereSelectionBuilder implements SelectionBuilder{
	private World world;
	private Vector3D offset;
	private Vector3D center;
	private double radius;
	private static final String OFFSET = "offset";
	private static final String CENTER = "center";
	private static final String RADIUS = "radius";
	
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
		if(label.equals(OFFSET)){
			Vector3D offset;
			try {
				offset = toVector3D(new BlockVector3D(posX, posY, posZ), shiftArgs);
			}catch(IllegalArgumentException e) {
				return false;
			}
			this.offset = offset;
			return true;
		}else if(label.equals(CENTER)) {
			Vector3D center;
			try {
				center = toVector3D(new BlockVector3D(posX, posY, posZ), shiftArgs);
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
				r = Double.parseDouble(args[1]);
			}catch(IllegalArgumentException e) {
				return false;
			}	
			if(r < 0) {
				return false;
			}
			this.radius = r;
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
		if(args[0].equals(CENTER) || args[0].equals(OFFSET)) {
			if(1 <= args.length && args.length <= 4) {
				return Arrays.asList("~");
			}else {
				return new ArrayList<>();
			}
		}else if(args[0].equals(RADIUS)) {
			if(args.length == 2) {
				return Arrays.asList("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0",
						"5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5", "10.0");
			}else {
				return new ArrayList<>();
			}
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
