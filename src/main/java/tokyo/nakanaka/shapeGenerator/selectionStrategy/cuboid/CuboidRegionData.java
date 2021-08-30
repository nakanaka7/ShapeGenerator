package tokyo.nakanaka.shapeGenerator.selectionStrategy.cuboid;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

/**
 * RegionData for cuboid selection
 */
public class CuboidRegionData implements RegionData{
	private Vector3D pos1;
	private Vector3D pos2;
	
	/**
	 * Get pos1
	 * @return pos1
	 */
	public Vector3D getPos1() {
		return pos1;
	}
	
	/**
	 * Set pos1
	 * @param pos1 pos1
	 */
	public void setPos1(Vector3D pos1) {
		this.pos1 = pos1;
	}
	
	/**
	 * Get pos2
	 * @return pos2
	 */
	public Vector3D getPos2() {
		return pos2;
	}
	
	/**
	 * Set pos2
	 * @param pos2 pos2
	 */
	public void setPos2(Vector3D pos2) {
		this.pos2 = pos2;
	}
	
}
