package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commandArgument.CoordinateCommandArgument;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.world.World;

public class SphereSelectionBuilder implements SelectionBuilder{
	private World world;
	private Vector3D offset;
	private SphereRegionBuilder sphereBuilder = new SphereRegionBuilder();
	private static final CoordinateCommandArgument coordArg = new CoordinateCommandArgument();
	private static final String OFFSET = "offset";
	private static final String CENTER = "center";
	private static final String RADIUS = "radius";
	
	@Override
	public World getWorld() {
		return this.world;
	}
	
	@Override
	public boolean onLeftClickBlock(int x, int y, int z) {
		this.sphereBuilder.setCenter(new Vector3D(x, y, z));
		return true;
	}

	@Override
	public boolean onRightClickBlock(int x, int y, int z) {
		Vector3D pos = new Vector3D(x, y, z);
		Vector3D center = this.sphereBuilder.getCenter();
		if(center == null) {
			return false;
		}
		double radius = Math.floor(pos.negate(center).getAbsolute()) + 0.5;
		this.sphereBuilder.setRadius(radius);
		return true;
	}

	@Override
	public boolean onCommand(int offsetX, int offsetY, int offsetZ, String[] args) {
		if(args.length == 0) {
			return false;
		}
		if(args[0].equals(OFFSET)){
			Vector3D offset;
			if(args.length == 1) {
				offset = new Vector3D(offsetX, offsetY, offsetZ);
			}else if(args.length == 4) {
				double x;
				double y;
				double z;
				try {
					x = coordArg.onParsingDouble(args[1], offsetX);
					y = coordArg.onParsingDouble(args[2], offsetY);
					z = coordArg.onParsingDouble(args[3], offsetZ);
				}catch(IllegalArgumentException e) {
					return false;
				}
				offset = new Vector3D(x, y, z);
			}else {
				return false;
			}
			this.offset = offset;
			return true;
		}else if(args[0].equals(CENTER)) {
			Vector3D pos;
			if(args.length == 1) {
				pos = new Vector3D(offsetX, offsetY, offsetZ);
			}else if(args.length == 4) {
				double x;
				double y;
				double z;
				try {
					x = coordArg.onParsingDouble(args[1], offsetX);
					y = coordArg.onParsingDouble(args[2], offsetY);
					z = coordArg.onParsingDouble(args[3], offsetZ);
				}catch(IllegalArgumentException e) {
					return false;
				}
				pos = new Vector3D(x, y, z);
			}else {
				return false;
			}
			this.sphereBuilder.setCenter(pos);
			return true;
		}else if(args[0].equals(RADIUS)) {
			if(args.length != 2) {
				return false;
			}
			double r;
			try {
				r = Double.parseDouble(args[1]);
			}catch(IllegalArgumentException e) {
				return false;
			}	
			this.sphereBuilder.setRadius(r);
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
		Vector3D center = this.sphereBuilder.getCenter();
		if(center != null) {
			line1 += center.getX() + "/ " + center.getY() + "/ " + center.getZ();
		}
		String line2 = RADIUS + ": ";
		Double r = this.sphereBuilder.getRadius();
		if(r != null) {
			line2 += r;
		}
		String line3 = OFFSET + ": ";
		if(offset == null) {
			line3 += "= center";
		}else {
			line3 += offset.getX() + "/ " + offset.getY() + "/ " + offset.getZ();
		}
		return Arrays.asList(line1, line2, line3);
	}

	@Override
	public Selection build() {
		if(this.world == null) {
			throw new IllegalStateException();
		}
		Region3D region = this.sphereBuilder.build();
		Vector3D center = this.sphereBuilder.getCenter();
		double radius = this.sphereBuilder.getRadius();
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
