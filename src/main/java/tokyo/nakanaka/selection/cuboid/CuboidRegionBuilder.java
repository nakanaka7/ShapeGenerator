package tokyo.nakanaka.selection.cuboid;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.CuboidRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;

public class CuboidRegionBuilder {
	private Vector3D pos1;
	private Vector3D pos2;
		
	public Vector3D getPos1() {
		return pos1;
	}

	public void setPos1(Vector3D pos1) {
		this.pos1 = pos1;
	}

	public Vector3D getPos2() {
		return pos2;
	}

	public void setPos2(Vector3D pos2) {
		this.pos2 = pos2;
	}

	public BoundRegion3D buildRegion() {
		if(pos1 == null || pos2 == null) {
			throw new IllegalStateException();
		}
		Region3D region = new CuboidRegion3D(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
		double ubx = Math.max(pos1.getX(), pos2.getX());
		double uby = Math.max(pos1.getY(), pos2.getY());
		double ubz = Math.max(pos1.getZ(), pos2.getZ());
		double lbx = Math.min(pos1.getX(), pos2.getX());
		double lby = Math.min(pos1.getY(), pos2.getY());
		double lbz = Math.min(pos1.getZ(), pos2.getZ());
		return new BoundRegion3D(region, ubx, uby, ubz, lbx, lby, lbz);
	}
}
