package tokyo.nakanaka.math.region3D;

public class Cuboid implements Region3D{
	private double x1;
	private double y1;
	private double z1;
	private double x2;
	private double y2;
	private double z2;
	
	public Cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
	}
	
	@Override
	public boolean contains(double x, double y, double z) {
		return ((x1 <= x && x <= x2) || (x2 <= x && x <= x1)) 
				&&((y1 <= y && y <= y2) || (y2 <= y && y <= y1)) 
				&&((z1 <= z && z <= z2) || (z2 <= z && z <= z1)); 
	}
}
