package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;

/**
 * Represents cuboid shape
 */
@PublicAPI
public class Cuboid implements Region3D{
	private double x1;
	private double y1;
	private double z1;
	private double x2;
	private double y2;
	private double z2;
	
	/**
	 * Construct a cuboid which diagonal corners are (x1, y1, z1) and (x2, y2 ,z1)
	 * @param x1 x coordinate of the first position
	 * @param y1 y coordinate of the first position
	 * @param z1 z coordinate of the first position
	 * @param x2 x coordinate of the second position
	 * @param y2 y coordinate of the second position
	 * @param z2 z coordinate of the second position
	 */
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
	
	/**
	 * Returns minimum x coordinate of this cuboid
	 * @return minimum x coordinate of this cuboid
	 */
	public double minX() {
		return Math.min(x1, x2);
	}
	
	/**
	 * Returns minimum y coordinate of this cuboid
	 * @return minimum y coordinate of this cuboid
	 */
	public double minY() {
		return Math.min(y1, y2);
	}
	
	/**
	 * Returns minimum z coordinate of this cuboid
	 * @return minimum z coordinate of this cuboid
	 */
	public double minZ() {
		return Math.min(z1, z2);
	}
	
	/**
	 * Returns maximum x coordinate of this cuboid
	 * @return maximum x coordinate of this cuboid
	 */
	public double maxX() {
		return Math.max(x1, x2);
	}
	
	/**
	 * Returns maximum y coordinate of this cuboid
	 * @return maximum y coordinate of this cuboid
	 */
	public double maxY() {
		return Math.max(y1, y2);
	}
	
	/**
	 * Returns maximum z coordinate of this cuboid
	 * @return maximum z coordinate of this cuboid
	 */
	public double maxZ() {
		return Math.max(z1, z2);
	}
	
}
