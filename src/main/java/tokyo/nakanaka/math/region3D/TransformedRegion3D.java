package tokyo.nakanaka.math.region3D;

import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;

public class TransformedRegion3D implements Region3D{
	private Region3D original;
	private LinearTransformation invTrans;
	
	/**
	 * @param original
	 * @param trans
	 * @throws SingularOperatorException
	 */
	public TransformedRegion3D(Region3D original, LinearTransformation trans) {
		this.original = original;
		this.invTrans = trans.getInverse();
	}

	@Override
	public boolean contains(double x, double y, double z) {
		Vector3D v = new Vector3D(x, y, z);
		Vector3D w = this.invTrans.apply(v);
		return this.original.contains(w.getX(), w.getY(), w.getZ());
	}
	
	
}
