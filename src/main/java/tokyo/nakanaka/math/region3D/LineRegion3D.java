package tokyo.nakanaka.math.region3D;

import tokyo.nakanaka.math.Vector3D;

/**
 * Represents a line between 2 points. It has thickness, and its ends are sphere. 
 */
public class LineRegion3D implements Region3D {
	private double x1;
	private double y1;
	private double z1;
	private double dx;
	private double dy;
	private double dz;
	private double thickness;
	
	/**
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param thickness must be positive
	 */
	
	public LineRegion3D(double x1, double y1, double z1, double x2, double y2, double z2, double thickness) {
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.dx = x2 - x1;
		this.dy = y2 - y1;
		this.dz = z2 - z1;
		if(thickness <= 0) {
			throw new IllegalStateException();
		}
		this.thickness = thickness;
	}
	
	@Override
	public boolean contains(double x, double y, double z) {
		x = x - this.x1;
		y = y - this.y1;
		z = z - this.z1;
		Vector3D p = new Vector3D(x, y, z);
		Vector3D a = new Vector3D(this.dx, this.dy, this.dz);
		if(p.getAbsolute() <= this.thickness / 2) {
			return true;
		}
		if(p.negate(a).getAbsolute() <= this.thickness / 2) {
			return true;
		}
		Vector3D e = a.divide(a.getAbsolute());//when |a| = 0, all points are included in the above 2 case
		double l = p.innerProduct(e);
		Vector3D ppara = e.multiply(l);
		double distance = p.negate(ppara).getAbsolute();
		return 0 <= l && l <= 1 && distance <= this.thickness / 2;
	}
	
	public boolean containsOld(double x, double y, double z) {
		x = x - this.x1;
		y = y - this.y1;
		z = z - this.z1;
		Vector3D p = new Vector3D(x, y, z);
		Vector3D a = new Vector3D(this.dx, this.dy, this.dz);
		if(p.getAbsolute() <= this.thickness / 2) {
			return true;
		}
		if(p.negate(a).getAbsolute() <= this.thickness / 2) {
			return true;
		}
		double t = p.innerProduct(a) / Math.pow(a.getAbsolute(), 2);
		double distance = p.negate(a.multiply(t)).getAbsolute();
		return 0 <= t && t <= 1 && distance <= this.thickness / 2;
	}

}
