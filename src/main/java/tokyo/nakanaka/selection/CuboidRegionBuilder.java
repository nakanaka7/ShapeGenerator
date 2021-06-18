package tokyo.nakanaka.selection;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.region3D.CuboidRegion3D;

public class CuboidRegionBuilder {
	private BlockVector3D pos1;
	private BlockVector3D pos2;
	
	public BlockVector3D getPos1() {
		return pos1;
	}
	
	public void setPos1(BlockVector3D pos1) {
		this.pos1 = pos1;
	}
	
	public BlockVector3D getPos2() {
		return pos2;
	}
	
	public void setPos2(BlockVector3D pos2) {
		this.pos2 = pos2;
	}
	
	public boolean setWidth(int width) {
		if(this.pos1 == null || width == 0) {
			return false;
		}
		BlockVector3D pos2a = this.pos2;
		if(pos2a == null) {
			pos2a = this.pos1;
		}
		if(width > 0) {
			this.pos2 = new BlockVector3D(this.pos1.getX() + width - 1, pos2a.getY(), pos2a.getZ());
		}else if(width < 0) {
			this.pos2 = new BlockVector3D(this.pos1.getX() + width + 1, pos2a.getY(), pos2a.getZ());
		}
		return true;
	}
	
	public Integer getWidth() {
		if(this.pos1 == null || this.pos2 == null) {
			return null;
		}
		int dx = this.pos2.getX() - this.pos1.getX();
		int sx = 1;
		if(dx != 0) {
			sx = dx / Math.abs(dx);
		}
		return dx + sx;
	}
	
	public boolean setHeight(int height) {
		if(this.pos1 == null || height == 0) {
			return false;
		}
		BlockVector3D pos2a = this.pos2;
		if(pos2a == null) {
			pos2a = this.pos1;
		}
		if(height > 0) {
			this.pos2 = new BlockVector3D(pos2a.getX(), this.pos1.getY() + height - 1, pos2a.getZ());
		}else if(height < 0) {
			this.pos2 = new BlockVector3D(pos2a.getX(), this.pos1.getY() + height + 1, pos2a.getZ());
		}
		return true;
	}
	
	public Integer getHight() {
		if(this.pos1 == null || this.pos2 == null) {
			return null;
		}
		int dy = this.pos2.getY() - this.pos1.getY();
		int sy = 1;
		if(dy != 0) {
			sy = dy / Math.abs(dy);
		}
		return dy + sy;
	}
	
	public boolean setLength(int length) {
		if(this.pos1 == null || length == 0) {
			return false;
		}
		BlockVector3D pos2a = this.pos2;
		if(pos2a == null) {
			pos2a = this.pos1;
		}
		if(length > 0) {
			this.pos2 = new BlockVector3D(pos2a.getX(), pos2a.getY(), this.pos1.getZ() + length - 1);
		}else if(length < 0) {
			this.pos2 = new BlockVector3D(pos2a.getX(), pos2a.getY(), this.pos1.getY() + length + 1);
		}
		return true;
	}
	
	public Integer getLength() {
		if(this.pos1 == null || this.pos2 == null) {
			return null;
		}
		int dz = this.pos2.getZ() - this.pos1.getZ();
		int sz = 1;
		if(dz != 0) {
			sz = dz / Math.abs(dz);
		}
		return dz + sz;
	}
	
	/**
	 * @throws IllegalStateException
	 */
	public CuboidRegion3D build() {
		if(this.pos1 == null || this.pos2 == null) {
			throw new IllegalStateException();
		}
		int intMinX = Math.min(pos1.getX(), pos2.getX());
		int intMinY = Math.min(pos1.getY(), pos2.getY());
		int intMinZ = Math.min(pos1.getZ(), pos2.getZ());
		int intMaxX = Math.max(pos1.getX(), pos2.getX());
		int intMaxY = Math.max(pos1.getY(), pos2.getY());
		int intMaxZ = Math.max(pos1.getZ(), pos2.getZ());
		double minX = intMinX - 0.5;
		double minY = intMinY - 0.5;
		double minZ = intMinZ - 0.5;
		double maxX = intMaxX + 0.5;
		double maxY = intMaxY + 0.5;
		double maxZ = intMaxZ + 0.5;
		return new CuboidRegion3D(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
}
