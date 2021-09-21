package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;

@PrivateAPI
public class RotatedRegion3D implements Region3D {
	private Region3D original;
	private Axis axis;
	private double degree;
	
	public RotatedRegion3D(Region3D original, Axis axis, double degree) {
		this.original = original;
		this.axis = axis;
		this.degree = degree;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		Vector3D pos = new Vector3D(x, y, z);
		LinearTransformation invTrans = LinearTransformation.ofRotation(this.axis, -this.degree);
		return this.original.contains(invTrans.apply(pos));
	}

}
