package tokyo.nakanaka.math.region3D;

import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;

public class TransformedRegion3D implements Region3D{
	private Region3D shiftOriginal;
	private LinearTransformation invTrans;
	private Vector3D offset;
	
	/**
	 * @param original
	 * @param trans
	 * @throws SingularOperatorException
	 */
	public TransformedRegion3D(Region3D original, LinearTransformation trans, Vector3D offset) {
		this.shiftOriginal = new ShiftedRegion3D(original, - offset.getX(), - offset.getY(), - offset.getZ());
		this.invTrans = trans.getInverse();
		this.offset = offset;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		Vector3D v = new Vector3D(x, y, z);
		v = v.negate(this.offset);
		v = this.invTrans.apply(v);
		return this.shiftOriginal.contains(v.getX(), v.getY(), v.getZ());
	}
	
	
}
