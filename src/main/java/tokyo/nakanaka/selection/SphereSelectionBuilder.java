package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commandArgument.CoordinateCommandArgument;
import tokyo.nakanaka.math.BlockVector3D;
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
	
	private void reset() {
		this.world = null;
		this.offset = null;
		this.sphereBuilder = new SphereRegionBuilder();
	}
	
	@Override
	public boolean onLeftClickBlock(World world, int x, int y, int z) {
		this.reset();
		this.world = world;
		this.sphereBuilder.setCenter(new BlockVector3D(x, y, z));
		return true;
	}

	@Override
	public boolean onRightClickBlock(World world, int x, int y, int z) {
		if(!world.equals(this.world)) {
			this.reset();
		}
		this.world = world;
		BlockVector3D pos = new BlockVector3D(x, y, z);
		BlockVector3D center = this.sphereBuilder.getCenter();
		int radius = (int)Math.floor(pos.negate(center).getAbsolute()) + 1;
		this.sphereBuilder.setRadius(radius);
		return true;
	}

	@Override
	public boolean onCommand(World world, int offsetX, int offsetY, int offsetZ, String[] args) {
		if(args.length == 0) {
			return false;
		}
		if(args[0].equals(OFFSET)){
			if(!world.equals(world)) {
				this.reset();
				return false;
			}
			if(args.length == 1) {
				this.offset = new Vector3D(offsetX, offsetY, offsetZ);
				return true;
			}else if(args.length == 4) {
				int x;
				int y;
				int z;
				try {
					x = coordArg.onParsing(args[1], offsetX);
					y = coordArg.onParsing(args[2], offsetY);
					z = coordArg.onParsing(args[3], offsetZ);
				}catch(IllegalArgumentException e) {
					return false;
				}
				this.offset = new Vector3D(x, y, z);
				return true;
			}else {
				return false;
			}
		}else if(args[0].equals(CENTER)) {
			BlockVector3D pos = null;
			if(args.length == 1) {
				pos = new BlockVector3D(offsetX, offsetY, offsetZ);
			}else if(args.length == 4) {
				int x;
				int y;
				int z;
				try {
					x = coordArg.onParsing(args[1], offsetX);
					y = coordArg.onParsing(args[2], offsetY);
					z = coordArg.onParsing(args[3], offsetZ);
				}catch(IllegalArgumentException e) {
					return false;
				}
				pos = new BlockVector3D(x, y, z);
			}
			if(pos == null) {
				return false;
			}
			this.world = world;
			this.sphereBuilder.setCenter(pos);
			this.offset = new Vector3D(pos.getX(), pos.getY(), pos.getZ());
			return true;
		}else if(args[0].equals(RADIUS)) {
			if(args.length != 2) {
				return false;
			}
			if(!world.equals(this.world)) {
				this.reset();
				return false;
			}
			int r;
			try {
				r = Integer.parseInt(args[1]);
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
				return Arrays.asList("1","2","3","4","5","10","20");
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
		BlockVector3D center = this.sphereBuilder.getCenter();
		if(center != null) {
			line1 += center.getX() + "/ " + center.getY() + "/ " + center.getZ();
		}
		String line2 = RADIUS + ": ";
		Integer r = this.sphereBuilder.getRadius();
		if(r != null) {
			line2 += r;
		}
		String line3 = OFFSET + ": ";
		if(offset != null) {
			line3 += offset.getX() + "/ " + offset.getY() + "/ " + offset.getZ();
		}
		return Arrays.asList(line1, line2, line3);
	}

	@Override
	public Selection build() {
		if(this.world == null || this.offset == null) {
			throw new IllegalStateException();
		}
		Region3D region = this.sphereBuilder.build();
		BlockVector3D center = this.sphereBuilder.getCenter();
		int radius = this.sphereBuilder.getRadius();
		double ubx = center.getX() + radius - 0.5;
		double uby = center.getY() + radius - 0.5;
		double ubz = center.getZ() + radius - 0.5;
		double lbx = center.getX() - radius + 0.5;
		double lby = center.getY() - radius + 0.5;
		double lbz = center.getZ() - radius + 0.5;
		BoundRegion3D boundRegion = new BoundRegion3D(region, ubx, uby, ubz, lbx, lby, lbz);
		BlockVector3D bo = new BlockVector3D((int)this.offset.getX(), (int)this.offset.getY(), (int)this.offset.getZ());
		return new Selection(this.world, boundRegion, bo);
	}

}
