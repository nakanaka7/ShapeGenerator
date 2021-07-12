package tokyo.nakanaka.math.boundRegion3D;

import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BlockRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;

public interface BoundRegion3D {
	Region3D getRegion3D();
	BoundRegion3D createShiftedRegion(Vector3D displacement);
	BoundRegion3D createTransformedRegion(LinearTransformation trans, Vector3D offset);
	BlockRegion3D toBlockRegion3D();
}
