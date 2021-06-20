package tokyo.nakanaka;

public enum Direction {
	NORTH(0, 0, - 1),
	SOUTH(0, 0, 1),
	EAST(1, 0, 0),
	WEST(-1, 0, 0),
	UP(0, 1, 0),
	DOWN(0, -1, 0);
	
	private int dx;
	private int dy;
	private int dz;
	
	private Direction(int dx, int dy, int dz) {
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}
	
	public int getX() {
		return this.dx;
	}
	
	public int getY() {
		return this.dy;
	}
	
	public int getZ() {
		return this.dz;
	}
}
