package tokyo.nakanaka.math.boundRegion3D;

import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BlockRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;

public interface BoundRegion3D {
	Region3D getRegion3D();
	BoundRegion3D createShiftedRegion(Vector3D displacement);
	BoundRegion3D createRotatedRegion(Axis axis, double degree, Vector3D offset);
	BlockRegion3D toBlockRegion3D();
}
