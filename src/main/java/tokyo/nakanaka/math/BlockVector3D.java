package tokyo.nakanaka.math;

public class BlockVector3D {
	private int x;
	private int y;
	private int z;
	
	public BlockVector3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getZ() {
		return this.z;
	}
	
	public BlockVector3D add(BlockVector3D vec) {
		return new BlockVector3D(this.x + vec.getX(), this.y + vec.getY(), this.z + vec.getZ());
	}
	
	public BlockVector3D negate(BlockVector3D vec) {
		return new BlockVector3D(this.x - vec.getX(), this.y - vec.getY(), this.z - vec.getZ());
	}
	
	public BlockVector3D multiply(int times) {
		return new BlockVector3D(times * this.x, times * this.y, times * this.z);
	}
	
	public double getAbsolute() {
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlockVector3D other = (BlockVector3D) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}
	
	
}
