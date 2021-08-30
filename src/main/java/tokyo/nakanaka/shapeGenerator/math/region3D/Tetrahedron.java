package tokyo.nakanaka.shapeGenerator.math.region3D;

import org.apache.commons.math3.linear.SingularOperatorException;

import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Matrix3x3;
import tokyo.nakanaka.math.Vector3D;

public class Tetrahedron implements Region3D {
	private Vector3D pos1;
	private LinearTransformation invTrans;
	private boolean hasRegion;
	
	public Tetrahedron(double x1, double y1, double z1,
			double x2, double y2, double z2,
			double x3, double y3, double z3,
			double x4, double y4, double z4) {
		this.pos1 = new Vector3D(x1, y1, z1);
		LinearTransformation trans 
			= new LinearTransformation(
					new Matrix3x3(x2 - x1, x3 - x1, x4 - x1,
							y2 - y1, y3 - y1, y4 - y1,
							z2 - z1, z3 - z1, z4 - z1));
		try {
			this.invTrans = trans.getInverse();
		}catch(SingularOperatorException e) {
			this.hasRegion = false;
		}
		this.hasRegion = true;
	}
	
	@Override
	public boolean contains(double x, double y, double z) {
		if(!this.hasRegion) {
			return false;
		}
		double px = x - pos1.getX();
		double py = y - pos1.getY();
		double pz = z - pos1.getZ();
		Vector3D c = this.invTrans.apply(new Vector3D(px, py, pz));
		double cx = c.getX();
		double cy = c.getY();
		double cz = c.getZ();
		double l = cx + cy + cz;
		return 0 <= cx && 0 <= cy && 0 <= cz && 0 <= l && l <= 1;
	}

}
