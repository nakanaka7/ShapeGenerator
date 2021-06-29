package tokyo.nakanaka.selection;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.world.World;

public class SelectionBuildingData {
	private World world;
	private RegionBuildingData regionData;
	private Vector3D offset;
	
	public SelectionBuildingData(World world, RegionBuildingData regionData) {
		this.world = world;
		this.regionData = regionData;
	}

	public World getWorld() {
		return world;
	}

	public RegionBuildingData getRegionData() {
		return regionData;
	}

	public Vector3D getOffset() {
		return offset;
	}

	public void setOffset(Vector3D offset) {
		this.offset = offset;
	}

}
