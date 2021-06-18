package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commandArgument.CoordinateCommandArgument;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.region3D.CuboidRegion3D;
import tokyo.nakanaka.world.World;

public class CuboidSelectionBuilder implements SelectionBuilder{
	private World world;
	private CuboidRegionBuilder cuboidBuilder = new CuboidRegionBuilder();
	private BlockVector3D offset;
	private static final CoordinateCommandArgument coorArg = new CoordinateCommandArgument();
	private static final String POS1 = "pos1";
	private static final String POS2 = "pos2";
	private static final String OFFSET = "offset";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String LENGTH = "length";
	
	private boolean setPos1(World world, int x, int y, int z) {
		if(!world.equals(this.world)) {
			this.reset();
		}
		this.world = world;
		BlockVector3D pos = new BlockVector3D(x, y, z);
		this.cuboidBuilder.setPos1(pos);
		this.offset = pos;
		return true;
	}
	
	private boolean setPos2(World world, int x, int y, int z) {
		if(!world.equals(this.world)) {
			this.reset();
		}
		this.world = world;
		BlockVector3D pos = new BlockVector3D(x, y, z);
		this.cuboidBuilder.setPos2(pos);
		return true;
	}
	
	@Override
	public boolean onLeftClickBlock(World world, int x, int y, int z) {
		return this.setPos1(world, x, y, z);
	}

	@Override
	public boolean onRightClickBlock(World world, int x, int y, int z) {
		return this.setPos2(world, x, y, z);
	}
	
	@Override
	public boolean onCommand(World world, int offsetX, int offsetY, int offsetZ, String[] args) {
		if(args.length == 0) {
			return false;
		}
		if(args[0].equals(OFFSET)){
			if(args.length == 1) {
				this.offset = new BlockVector3D(offsetX, offsetY, offsetZ);
				return true;
			}else if(args.length == 4) {
				int x;
				int y;
				int z;
				try {
					x = coorArg.onParsing(args[1], offsetX);
					y = coorArg.onParsing(args[2], offsetY);
					z = coorArg.onParsing(args[3], offsetZ);
				}catch(IllegalArgumentException e) {
					return false;
				}
				this.offset = new BlockVector3D(x, y, z);
				return true;
			}else {
				return false;
			}
		}else if(args[0].equals(POS1)) {
			if(args.length == 1) {
				return this.setPos1(world, offsetX, offsetY, offsetZ);
			}else if(args.length == 4) {
				int x;
				int y;
				int z;
				try {
					x = coorArg.onParsing(args[1], offsetX);
					y = coorArg.onParsing(args[2], offsetY);
					z = coorArg.onParsing(args[3], offsetZ);
				}catch(IllegalArgumentException e) {
					return false;
				}
				return this.setPos1(world, x, y, z);
			}else {
				return false;
			}
		}else if(args[0].equals(POS2)) {
			if(args.length == 1) {
				return this.setPos2(world, offsetX, offsetY, offsetZ);
			}else if(args.length == 4) {
				int x;
				int y;
				int z;
				try {
					x = coorArg.onParsing(args[1], offsetX);
					y = coorArg.onParsing(args[2], offsetY);
					z = coorArg.onParsing(args[3], offsetZ);
				}catch(IllegalArgumentException e) {
					return false;
				}
				return this.setPos2(world, x, y, z);
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
		int upperBoundX = Math.max(pos1.getX(), pos2.getX());
		int upperBoundY = Math.max(pos1.getY(), pos2.getY());
		int upperBoundZ = Math.max(pos1.getZ(), pos2.getZ());
		int lowerBoundX = Math.min(pos1.getX(), pos2.getX());
		int lowerBoundY = Math.min(pos1.getY(), pos2.getY());
		int lowerBoundZ = Math.min(pos1.getZ(), pos2.getZ());
		return new Selection(this.world, region, upperBoundX, upperBoundY, upperBoundZ, lowerBoundX, lowerBoundY, lowerBoundZ, offset.getX(), offset.getY(), offset.getZ());
	}

	public void reset() {
		this.world = null;
		this.cuboidBuilder = new CuboidRegionBuilder();
		this.offset = null;
	}

}
