package tokyo.nakanaka.shapeGenerator.math.region3D;

import org.apache.commons.math3.linear.SingularOperatorException;

import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;

public class AffineTransformedRegion3D implements Region3D{
	private Region3D original;
	private LinearTransformation linearTrans;
	private LinearTransformation invLinearTrans;
	private Vector3D offset;
	
	public AffineTransformedRegion3D(Region3D original, LinearTransformation linearTrans, Vector3D offset) {
		this.original = original;
		this.linearTrans = linearTrans;
		try {
			this.invLinearTrans = linearTrans.getInverse();
		}catch(SingularOperatorException e) {
			throw new IllegalArgumentException();
		}
		this.offset = offset;
	}

	public Region3D getOriginalRegion3D() {
		return original;
	}

	public LinearTransformation getLinearTransformation() {
		return linearTrans;
	}

	public Vector3D getOffset() {
		return offset;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		Vector3D pos = new Vector3D(x, y, z);
		pos = pos.negate(this.offset);
		pos = this.invLinearTrans.apply(pos);
		return this.original.contains(pos.getX(), pos.getY(), pos.getZ());
	}

}
