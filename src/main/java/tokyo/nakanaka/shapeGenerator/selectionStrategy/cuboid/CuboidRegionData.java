package tokyo.nakanaka.shapeGenerator.selectionStrategy.cuboid;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

/**
 * RegionData for cuboid selection
 */
public class CuboidRegionData implements RegionData{
	private Vector3D pos1 = Vector3D.ORIGIN;
	private Vector3D pos2 = Vector3D.ORIGIN;
	
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

	@Override
	public BoundRegion3D createBoundRegion3D() {
		Region3D region = new Cuboid(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
		double ubx = Math.max(pos1.getX(), pos2.getX());
		double uby = Math.max(pos1.getY(), pos2.getY());
		double ubz = Math.max(pos1.getZ(), pos2.getZ());
		double lbx = Math.min(pos1.getX(), pos2.getX());
		double lby = Math.min(pos1.getY(), pos2.getY());
		double lbz = Math.min(pos1.getZ(), pos2.getZ());
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Override
	public Vector3D getDefaultOffset() {
		return this.pos1;
	}
	
}
