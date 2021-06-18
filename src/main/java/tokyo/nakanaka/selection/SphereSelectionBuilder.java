package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commandArgument.CoordinateCommandArgument;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.world.World;

public class SphereSelectionBuilder implements SelectionBuilder{
	private World world;
	private BlockVector3D offset;
	private SphereRegionBuilder sphereBuilder = new SphereRegionBuilder();
	private static final CoordinateCommandArgument coordArg = new CoordinateCommandArgument();
	private static final String OFFSET = "offset";
	private static final String CENTER = "center";
	private static final String R = "r";
	
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
				this.offset = new BlockVector3D(offsetX, offsetY, offsetZ);
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
				this.offset = new BlockVector3D(x, y, z);
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
			this.offset = pos;
			return true;
		}else if(args[0].equals(R)) {
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
			return Arrays.asList(CENTER, R, OFFSET);
		}
		if(args[0].equals(CENTER) || args[0].equals(OFFSET)) {
			if(1 <= args.length && args.length <= 4) {
				return Arrays.asList("~");
			}else {
				return new ArrayList<>();
			}
		}else if(args[0].equals(R)) {
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
		String line2 = R + ": ";
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
		return new Selection(this.world, region,
				center.getX() + radius - 1,
				center.getY() + radius - 1,
				center.getZ() + radius - 1,
				center.getX() - radius + 1,
				center.getY() - radius + 1,
				center.getZ() - radius + 1,
				this.offset.getX(), this.offset.getY(), this.offset.getZ());
	}

}
