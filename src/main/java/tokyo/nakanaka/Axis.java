package tokyo.nakanaka.geometricProperty;

import tokyo.nakanaka.math.Vector3D;

public enum Axis {
	X(1, 0, 0),
	Y(0, 1, 0),
	Z(0, 0, 1);
	
	private double x;
	private double y;
	private double z;
	
	private Axis(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D toVector3D() {
		return new Vector3D(this.x, this.y, this.z);
	}
	
}
