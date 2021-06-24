package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commandArgument.CoordinateCommandArgument;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.CuboidRegion3D;
import tokyo.nakanaka.world.World;

public class CuboidSelectionBuilder implements SelectionBuilder{
	private World world;
	private CuboidRegionBuilder cuboidBuilder = new CuboidRegionBuilder();
	private Vector3D offset;
	private static final CoordinateCommandArgument coordArg = new CoordinateCommandArgument();
	private static final String POS1 = "pos1";
	private static final String POS2 = "pos2";
	private static final String OFFSET = "offset";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String LENGTH = "length";
	
	public CuboidSelectionBuilder(World world) {
		this.world = world;
	}

	@Override
	public World getWorld() {
		return this.world;
	}
	
	private boolean setPos1(int x, int y, int z) {
		BlockVector3D pos = new BlockVector3D(x, y, z);
		this.cuboidBuilder.setPos1(pos);
		this.offset = new Vector3D(x, y, z);
		return true;
	}
	
	private boolean setPos2(int x, int y, int z) {
		BlockVector3D pos = new BlockVector3D(x, y, z);
		this.cuboidBuilder.setPos2(pos);
		return true;
	}
	
	@Override
	public boolean onLeftClickBlock(int x, int y, int z) {
		return this.setPos1(x, y, z);
	}

	@Override
	public boolean onRightClickBlock(int x, int y, int z) {
		return this.setPos2(x, y, z);
	}
	
	@Override
	public boolean onCommand(int posX, int posY, int posZ, String[] args) {
		if(args.length == 0) {
			return false;
		}
		if(args[0].equals(OFFSET)){
			if(args.length == 1) {
				this.offset = new Vector3D(posX, posY, posZ);
				return true;
			}else if(args.length == 4) {
				int x;
				int y;
				int z;
				try {
					x = coordArg.onParsingInt(args[1], posX);
					y = coordArg.onParsingInt(args[2], posY);
					z = coordArg.onParsingInt(args[3], posZ);
				}catch(IllegalArgumentException e) {
					return false;
				}
				this.offset = new Vector3D(x, y, z);
				return true;
			}else {
				return false;
			}
		}else if(args[0].equals(POS1)) {
			if(args.length == 1) {
				return this.setPos1(posX, posY, posZ);
			}else if(args.length == 4) {
				int x;
				int y;
				int z;
				try {
					x = coordArg.onParsingInt(args[1], posX);
					y = coordArg.onParsingInt(args[2], posY);
					z = coordArg.onParsingInt(args[3], posZ);
				}catch(IllegalArgumentException e) {
					return false;
				}
				return this.setPos1(x, y, z);
			}else {
				return false;
			}
		}else if(args[0].equals(POS2)) {
			if(args.length == 1) {
				return this.setPos2(posX, posY, posZ);
			}else if(args.length == 4) {
				int x;
				int y;
				int z;
				try {
					x = coordArg.onParsingInt(args[1], posX);
					y = coordArg.onParsingInt(args[2], posY);
					z = coordArg.onParsingInt(args[3], posZ);
				}catch(IllegalArgumentException e) {
					return false;
				}
				return this.setPos2(x, y, z);
			}else {
				return false;
			}
		}else if(args[0].equals(WIDTH)) {
			if(args.length != 2) {
				return false;
			}
			int width;
			try {
				width = Integer.parseInt(args[1]);
			}catch(IllegalArgumentException e) {
				return false;
			}	
			return this.cuboidBuilder.setWidth(width);
		}else if(args[0].equals(HEIGHT)) {
			if(args.length != 2) {
				return false;
			}
			int height;
			try {
				height = Integer.parseInt(args[1]);
			}catch(IllegalArgumentException e) {
				return false;
			}	
			return this.cuboidBuilder.setHeight(height);
		}else if(args[0].equals(LENGTH)) {
			if(args.length != 2) {
				return false;
			}
			int length;
			try {
				length = Integer.parseInt(args[1]);
			}catch(IllegalArgumentException e) {
				return false;
			}
			return this.cuboidBuilder.setLength(length);
		}else {
			return false;
		}
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		if(args.length == 0) {
			return new ArrayList<>();
		}else if(args.length == 1) {
			return Arrays.asList(POS1, POS2, OFFSET, WIDTH, LENGTH, HEIGHT);
		}else if((args[0].equals(POS1) || args[0].equals(POS2) || args[0].equals(OFFSET))) {
			if(args.length <= 4) {
				return Arrays.asList("~");
			}
		}else if(args[0].equals(HEIGHT) || args[0].equals(LENGTH) || args[0].equals(WIDTH)) {
			if(args.length == 2) {
				return Arrays.asList("-10", "-20", "-30", "10", "20", "30");
			}
		}
		return new ArrayList<>();
	}
	
	@Override
	public List<String> getStateLines() {
		String line1 = POS1 + ": ";
		BlockVector3D pos1 = this.cuboidBuilder.getPos1();
		if(pos1 != null) {
			line1 += pos1.getX() + "/ " + pos1.getY() + "/ " + pos1.getZ();
		}
		String line2 = POS2 + ": ";
		BlockVector3D pos2 = this.cuboidBuilder.getPos2();
		if(pos2 != null) {
			line2 += pos2.getX() + "/ " + pos2.getY() + "/ " + pos2.getZ();
		}
		String line3 = OFFSET + ": ";
		if(offset != null) {
			line3 += offset.getX() + "/ " + offset.getY() + "/ " + offset.getZ();
		}
		String line4 = WIDTH + ": ";
		String line5 = HEIGHT + ": ";
		String line6 = LENGTH + ": ";
		if(pos1 != null && pos2 != null) {
			line4 += String.valueOf(this.cuboidBuilder.getWidth());
			line5 += String.valueOf(this.cuboidBuilder.getHight());
			line6 += String.valueOf(this.cuboidBuilder.getLength());
		}
		return Arrays.asList(line1, line2, line3, line4, line5, line6);
	}
	
	@Override
	public Selection build() {
		if(this.world == null || this.offset == null) {
			throw new IllegalStateException();
		}
		CuboidRegion3D region = this.cuboidBuilder.build();
		BlockVector3D pos1 = this.cuboidBuilder.getPos1();
		BlockVector3D pos2 = this.cuboidBuilder.getPos2();
		double ubx = Math.max(pos1.getX(), pos2.getX()) + 0.5;
		double uby = Math.max(pos1.getY(), pos2.getY()) + 0.5;
		double ubz = Math.max(pos1.getZ(), pos2.getZ()) + 0.5;
		double lbx = Math.min(pos1.getX(), pos2.getX()) - 0.5;
		double lby = Math.min(pos1.getY(), pos2.getY()) - 0.5;
		double lbz = Math.min(pos1.getZ(), pos2.getZ()) - 0.5;
		BoundRegion3D boundRegion = new BoundRegion3D(region, ubx, uby, ubz, lbx, lby, lbz);
		return new Selection(this.world, boundRegion, this.offset);
	}

	@Deprecated
	public void reset() {
		this.world = null;
		this.cuboidBuilder = new CuboidRegionBuilder();
		this.offset = null;
	}

}
