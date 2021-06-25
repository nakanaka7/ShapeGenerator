package tokyo.nakanaka.selection;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.world.World;

public class CuboidSelectionBuilderOld extends AbstractSelectionBuilder{
	public CuboidSelectionBuilderOld(World world) {
		super(world, new CuboidRegionBuilder());
	}

	@Override
	String getDefaultOffsetName() {
		return CuboidRegionBuilder.getStringPos1();
	}

	@Override
	Vector3D getDefaultOffset() {
		return ((CuboidRegionBuilder)this.builder).getPos1();
	}

}
