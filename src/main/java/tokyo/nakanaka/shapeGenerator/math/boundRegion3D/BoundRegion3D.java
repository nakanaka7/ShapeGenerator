package tokyo.nakanaka.shapeGenerator.math.boundRegion3D;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.BlockRegion3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

public interface BoundRegion3D {
	Region3D getRegion3D();
	double getUpperBoundX();
	double getUpperBoundY();
	double getUpperBoundZ();
	double getLowerBoundX();
	double getLowerBoundY();
	double getLowerBoundZ();
	BoundRegion3D createShiftedRegion(Vector3D displacement);
	BoundRegion3D createScaledRegion(Axis axis, double factor, Vector3D offset);
	BoundRegion3D createMirroredRegion(Axis axis, Vector3D offset);
	BoundRegion3D createRotatedRegion(Axis axis, double degree, Vector3D offset);
	BlockRegion3D toBlockRegion3D();
}
