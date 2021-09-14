package tokyo.nakanaka.shapeGenerator.math.boundRegion3D;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.BlockRegion3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

@PublicAPI
public interface BoundRegion3D {
	@PrivateAPI
	Region3D getRegion3D();
	@PrivateAPI
	double getUpperBoundX();
	@PrivateAPI
	double getUpperBoundY();
	@PrivateAPI
	double getUpperBoundZ();
	@PrivateAPI
	double getLowerBoundX();
	@PrivateAPI
	double getLowerBoundY();
	@PrivateAPI
	double getLowerBoundZ();
	@PrivateAPI
	BoundRegion3D createShiftedRegion(Vector3D displacement);
	@PrivateAPI
	BoundRegion3D createScaledRegion(Axis axis, double factor, Vector3D offset);
	@PrivateAPI
	BoundRegion3D createMirroredRegion(Axis axis, Vector3D offset);
	@PrivateAPI
	BoundRegion3D createRotatedRegion(Axis axis, double degree, Vector3D offset);
	@PrivateAPI
	BlockRegion3D toBlockRegion3D();
}
