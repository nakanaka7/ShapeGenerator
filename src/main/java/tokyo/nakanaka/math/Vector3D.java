package tokyo.nakanaka.math;

public class Vector3D {
	public static final Vector3D ORIGIN = new Vector3D(0, 0, 0);
	public static final Vector3D ZERO = new Vector3D(0, 0, 0);
	public static final Vector3D PLUS_I = new Vector3D(1, 0, 0);
	public static final Vector3D MINUS_I = new Vector3D(- 1, 0, 0);
	public static final Vector3D PLUS_J = new Vector3D(0, 1, 0);
	public static final Vector3D MINUS_J = new Vector3D(0, - 1, 0);
	public static final Vector3D PLUS_K = new Vector3D(0, 0, 1);
	public static final Vector3D MINUS_K = new Vector3D(0, 0, - 1);
	
	private double x;
	private double y;
	private double z;
	
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getZ() {
		return this.z;
	}
	
	public Vector3D add(Vector3D other) {
		return new Vector3D(this.x + other.getX(), this.y + other.getY(), this.z + other.getZ());
	}
	
	public Vector3D negate(Vector3D other) {
		return new Vector3D(this.x - other.getX(), this.y - other.getY(), this.z - other.getZ());
	}
	
	public Vector3D multiply(double a) {
		return new Vector3D(a * x, a * y, a * z);
	}
	
	/**
	 * @param a
	 * @return
	 * @throws ArithmeticException
	 */
	public Vector3D divide(double a) {
		return new Vector3D(x / a, y / a, z / a);
	}
	
	public double innerProduct(Vector3D other) {
		return x * other.getX() + y * other.getY() + z * other.getZ();
	}
	
	public Vector3D crossProduct(Vector3D other) {
		double x2 = other.getX();
		double y2 = other.getY();
		double z2 = other.getZ();
		double x3 = this.y * z2 - this.z * y2;
		double y3 = this.z * x2 - this.x * z2;
		double z3 = this.x * y2 - this.y * x2;
		return new Vector3D(x3, y3, z3);
	}
	
	public double getAbsolute() {
		return Math.sqrt(this.innerProduct(this));
	}
	
	public double distance(Vector3D other) {
		return this.negate(other).getAbsolute();
	}
	
	@Override
	public String toString() {
		return x + "/ " + y + "/ " + z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Vector3D other = (Vector3D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
	
}
