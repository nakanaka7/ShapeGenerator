package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.annotation.PrivateAPI;

@PrivateAPI
public class MirroredRegion3D implements Region3D {
	private Region3D original;
	private Axis axis;
	
	public MirroredRegion3D(Region3D original, Axis axis) {
		this.original = original;
		this.axis = axis;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return switch(this.axis) {
		case X -> this.original.contains(- x, y, z);
		case Y -> this.original.contains(x, - y, z);
		case Z -> this.original.contains(x, y, - z);
		};
	}

}
