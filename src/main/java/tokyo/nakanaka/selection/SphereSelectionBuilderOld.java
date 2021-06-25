package tokyo.nakanaka.selection;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.world.World;

public class SphereSelectionBuilderOld extends AbstractSelectionBuilderOld{

	public SphereSelectionBuilderOld(World world) {
		super(world, new SphereRegionBuilder());
	}

	@Override
	String getDefaultOffsetName() {
		return SphereRegionBuilder.getStringCenter();
	}

	@Override
	Vector3D getDefaultOffset() {
		return ((SphereRegionBuilder)builder).getCenter();
	}

}
