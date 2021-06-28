package tokyo.nakanaka.selection;

import tokyo.nakanaka.ValuePool;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.world.World;

public class SelectionData {
	private World world;
	private ValuePool regionPool = new ValuePool();
	private Vector3D offset;
	
	public SelectionData(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}

	public ValuePool getRegionPool() {
		return regionPool;
	}

	public Vector3D getOffset() {
		return offset;
	}

	public void setOffset(Vector3D offset) {
		this.offset = offset;
	}
	
}
