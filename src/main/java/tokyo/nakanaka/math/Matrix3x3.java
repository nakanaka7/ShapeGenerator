package tokyo.nakanaka.math;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;

public class Matrix3x3 {
	private double e00;
	private double e01;
	private double e02;
	private double e10;
	private double e11;
	private double e12;
	private double e20;
	private double e21;
	private double e22;
	
	/**
	Represents the following 3 x 3 matrix
		|e00 e01 e02|
		|e10 e11 e12|
		|e20 e21 e22|
	*/
	public Matrix3x3(double e00, double e01, double e02, double e10, double e11, double e12, double e20, double e21,
			double e22) {
		this.e00 = e00;
		this.e01 = e01;
		this.e02 = e02;
		this.e10 = e10;
		this.e11 = e11;
		this.e12 = e12;
		this.e20 = e20;
		this.e21 = e21;
		this.e22 = e22;
	}
	
	public Vector3D apply(Vector3D v) {
		double x = v.getX();
		double y = v.getY();
		double z = v.getZ();
		double sx = e00 * x + e01 * y + e02 * z;
		double sy = e10 * x + e11 * y + e12 * z;
		double sz = e20 * x + e21 * y + e22 * z;
		return new Vector3D(sx, sy, sz);
	}
	
	public Matrix3x3 multiply(Matrix3x3 m) {
		Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(new double[][] {{e00, e01, e02}, {e10, e11, e12}, {e20, e21, e22}});
		Array2DRowRealMatrix matrixM = new Array2DRowRealMatrix(new double[][] {{m.e00, m.e01, m.e02}, {m.e10, m.e11, m.e12}, {m.e20, m.e21, m.e22}});
		double[][] a = matrix.multiply(matrixM).getData();
		return new Matrix3x3(a[0][0], a[0][1], a[0][2],
				a[1][0], a[1][1], a[1][2],
				a[2][0], a[2][1], a[2][2]);
	}
	
	public Matrix3x3 getInverse() {
		Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(new double[][] {{e00, e01, e02}, {e10, e11, e12}, {e20, e21, e22}});
		double[][] a = MatrixUtils.inverse(matrix).getData();
		return new Matrix3x3(a[0][0], a[0][1], a[0][2],
				a[1][0], a[1][1], a[1][2],
				a[2][0], a[2][1], a[2][2]);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(e00);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(e01);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(e02);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(e10);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(e11);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(e12);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(e20);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(e21);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(e22);
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
		Matrix3x3 other = (Matrix3x3) obj;
		if (Double.doubleToLongBits(e00) != Double.doubleToLongBits(other.e00))
			return false;
		if (Double.doubleToLongBits(e01) != Double.doubleToLongBits(other.e01))
			return false;
		if (Double.doubleToLongBits(e02) != Double.doubleToLongBits(other.e02))
			return false;
		if (Double.doubleToLongBits(e10) != Double.doubleToLongBits(other.e10))
			return false;
		if (Double.doubleToLongBits(e11) != Double.doubleToLongBits(other.e11))
			return false;
		if (Double.doubleToLongBits(e12) != Double.doubleToLongBits(other.e12))
			return false;
		if (Double.doubleToLongBits(e20) != Double.doubleToLongBits(other.e20))
			return false;
		if (Double.doubleToLongBits(e21) != Double.doubleToLongBits(other.e21))
			return false;
		if (Double.doubleToLongBits(e22) != Double.doubleToLongBits(other.e22))
			return false;
		return true;
	}
	
	
}
