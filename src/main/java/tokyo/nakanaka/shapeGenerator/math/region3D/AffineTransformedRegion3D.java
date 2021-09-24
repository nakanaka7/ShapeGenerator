package tokyo.nakanaka.shapeGenerator.math.region3D;

import org.apache.commons.math3.linear.SingularOperatorException;

import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;

@PrivateAPI
public class AffineTransformedRegion3D implements Region3D{
	private Region3D original;
	private LinearTransformation linearTrans;
	private LinearTransformation invLinearTrans;
	private Vector3D dis;
	
	/**
	 * Constructs a region given by applying an affine transformation to the original region.
	 * This transformation is the combination of a linear transformation and a subsequent displacement.
	 * @param original an original region
	 * @param linearTrans a linear transformation of the affine transformation
	 * @param dis a displacement of the affine transformation
	 * @throws IllegalArgumentException if the linearTrnas is a singular one 
	 */
	
	public AffineTransformedRegion3D(Region3D original, LinearTransformation linearTrans, Vector3D dis) {
		this.original = original;
		this.linearTrans = linearTrans;
		try {
			this.invLinearTrans = linearTrans.getInverse();
		}catch(SingularOperatorException e) {
			throw new IllegalArgumentException();
		}
		this.dis = dis;
	}

	public Region3D originalRegion3D() {
		return original;
	}

	/**
	 * Returns a linear transformation of the affine transformation
	 * @return a linear transformation of the affine transformation
	 */
	public LinearTransformation linearTransformation() {
		return linearTrans;
	}

	/**
	 * Returns a displacement of the affine transformation
	 * @return a displacement of the affine transformation
	 */
	public Vector3D displacement() {
		return dis;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		Vector3D pos = new Vector3D(x, y, z);
		pos = pos.negate(this.dis);
		pos = this.invLinearTrans.apply(pos);
		return this.original.contains(pos);
	}

}
