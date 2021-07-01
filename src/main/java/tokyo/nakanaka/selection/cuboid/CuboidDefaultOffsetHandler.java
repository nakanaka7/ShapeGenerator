package tokyo.nakanaka.selection.cuboid;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.DefaultOffsetHandler;
import tokyo.nakanaka.selection.RegionBuilder;
@Deprecated
public class CuboidDefaultOffsetHandler implements DefaultOffsetHandler{

	@Override
	public String getDefaultOffsetLabel() {
		return "pos1";
	}

	@Override
	public Vector3D getDefaultOffset(RegionBuilder builder) {
		CuboidRegionBuilder cuboidBuilder = (CuboidRegionBuilder)builder;
		return cuboidBuilder.getPos1();
	}

}
