package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commandArgument.CoordinateArgument;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.region3D.CuboidRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.world.World;

public class CuboidSelectionBuilder implements SelectionBuilder{
	private World world;
	private BlockVector3D pos1;
	private BlockVector3D pos2;
	private BlockVector3D offset;
	private static CoordinateArgument coorArg = new CoordinateArgument();
	private static String POS1 = "pos1";
	private static String POS2 = "pos2";
	private static String OFFSET = "offset";
	private static String WIDTH = "width";
	private static String HEIGHT = "height";
	private static String LENGTH = "length";
	
	private void setPos1(World world, int x, int y, int z) {
		if(!world.equals(this.world)) {
			pos2 = null;
		}
		this.world = world;
		this.pos1 = new BlockVector3D(x, y, z);
		this.offset = new BlockVector3D(x, y, z);
	}
	
	private void setPos2(World world, int x, int y, int z) {
		if(!world.equals(this.world)) {
			pos1 = null;
		}
		this.world = world;
		this.pos2 = new BlockVector3D(x, y, z);
	}
	
	@Override
	public boolean onLeftClickBlock(World world, int x, int y, int z) {
		this.setPos1(world, x, y, z);
		return true;
	}

	@Override
	public boolean onRightClickBlock(World world, int x, int y, int z) {
		this.setPos2(world, x, y, z);
		return true;
	}
	
	@Override
	public boolean onCommand(World world, int offsetX, int offsetY, int offsetZ, String[] args) {
		if(args.length == 0) {
			return false;
		}
		if(args[0].equals(POS1)) {
			if(args.length == 1) {
				this.setPos1(world, offsetX, offsetY, offsetZ);
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
				this.setPos1(world, x, y, z);
				return true;
			}else {
				return false;
			}
		}else if(args[0].equals(POS2)) {
			if(args.length == 1) {
				this.setPos2(world, offsetX, offsetY, offsetZ);
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
				this.setPos2(world, x, y, z);
				return true;
			}else {
				return false;
			}
		}else if(args[0].equals(OFFSET)){
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
		}else if(args[0].equals(WIDTH)) {
			if(args.length == 2) {
				if(pos1 == null) {
					return false;
				}
				if(pos2 == null) {
					pos2 = pos1;
				}
				int width;
				try {
					width = Integer.parseInt(args[1]);
				}catch(IllegalArgumentException e) {
					return false;
				}
				if(width > 0) {
					pos2 = new BlockVector3D(pos1.getX() + width - 1, pos2.getY(), pos2.getZ());
					return true;
				}else if(width < 0) {
					pos2 = new BlockVector3D(pos1.getX() + width + 1, pos2.getY(), pos2.getZ());
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}else if(args[0].equals(HEIGHT)) {
			if(args.length == 2) {
				if(pos1 == null) {
					return false;
				}
				if(pos2 == null) {
					pos2 = pos1;
				}
				int height;
				try {
					height = Integer.parseInt(args[1]);
				}catch(IllegalArgumentException e) {
					return false;
				}
				if(height > 0) {
					pos2 = new BlockVector3D(pos2.getX(), pos1.getY() + height - 1, pos2.getZ());
					return true;
				}else if(height < 0) {
					pos2 = new BlockVector3D(pos2.getX(), pos1.getY() + height + 1, pos2.getZ());
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}else if(args[0].equals(LENGTH)) {
			if(args.length == 2) {
				if(pos1 == null) {
					return false;
				}
				if(pos2 == null) {
					pos2 = pos1;
				}
				int length;
				try {
					length = Integer.parseInt(args[1]);
				}catch(IllegalArgumentException e) {
					return false;
				}
				if(length > 0) {
					pos2 = new BlockVector3D(pos2.getX(), pos2.getY(), pos1.getZ() + length - 1);
					return true;
				}else if(length < 0) {
					pos2 = new BlockVector3D(pos2.getX(), pos2.getY(), pos1.getZ() + length + 1);
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
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
		if(pos1 != null) {
			line1 += pos1.getX() + "/ " + pos1.getY() + "/ " + pos1.getZ();
		}
		String line2 = POS2 + ": ";
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
			int dx = pos2.getX() - pos1.getX();
			int dy = pos2.getY() - pos1.getY();
			int dz = pos2.getZ() - pos1.getZ();
			int sx = 1;
			int sy = 1;
			int sz = 1;
			if(dx != 0) {
				sx = dx / Math.abs(dx);
			}
			if(dy != 0) {
				sy = dy / Math.abs(dy);
			}
			if(dz != 0) {
				sz = dz / Math.abs(dz);
			}
			WIDTH += String.valueOf(dx + sx);
			HEIGHT += String.valueOf(dy + sy);
			LENGTH += String.valueOf(dz + sz);
		}
		return Arrays.asList(line1, line2, line3, line4, line5, line6);
	}
	
	@Override
	public Selection build() {
		if(this.world == null || this.pos1 == null || this.pos2 == null || this.offset == null) {
			throw new IllegalStateException();
		}
		int upperBoundX = Math.max(pos1.getX(), pos2.getX());
		int upperBoundY = Math.max(pos1.getY(), pos2.getY());
		int upperBoundZ = Math.max(pos1.getZ(), pos2.getZ());
		int lowerBoundX = Math.min(pos1.getX(), pos2.getX());
		int lowerBoundY = Math.min(pos1.getY(), pos2.getY());
		int lowerBoundZ = Math.min(pos1.getZ(), pos2.getZ());
		double minX = lowerBoundX - 0.5;
		double minY = lowerBoundY - 0.5;
		double minZ = lowerBoundZ - 0.5;
		double maxX = upperBoundX + 0.5;
		double maxY = upperBoundY + 0.5;
		double maxZ = upperBoundZ + 0.5;
		Region3D region = new CuboidRegion3D(minX, minY, minZ, maxX, maxY, maxZ);
		return new Selection(world, region, upperBoundX, upperBoundY, upperBoundZ, lowerBoundX, lowerBoundY, lowerBoundZ, offset.getX(), offset.getY(), offset.getZ());
	}

}
