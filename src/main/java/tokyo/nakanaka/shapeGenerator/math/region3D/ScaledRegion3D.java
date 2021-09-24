package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.annotation.PrivateAPI;

@PrivateAPI
public class ScaledRegion3D implements Region3D {
	private Region3D original;
	private Axis axis;
	private double factor;
	
	/**
	 * @param original
	 * @param axis
	 * @param factor
	 * @throws IllegalArgumentException if factor is zero
	 */
	public ScaledRegion3D(Region3D original, Axis axis, double factor) {
		if(factor == 0) {
			throw new IllegalArgumentException();
		}
		this.original = original;
		this.axis = axis;
		this.factor = factor;
	}
	
	@Override
	public boolean contains(double x, double y, double z) {
		switch(this.axis) {
		case X -> x = x / factor;
		case Y -> y = y / factor;
		case Z -> z = z / factor;
		}
		return this.original.contains(x, y, z);
	}

}
