package tokyo.nakanaka.shapeGenerator.regionData;

import tokyo.nakanaka.math.Vector3D;

public class TetrahedronRegionData implements RegionData {
	private Vector3D pos1;
	private Vector3D pos2;
	private Vector3D pos3;
	private Vector3D pos4;
	
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
	
	public Vector3D getPos3() {
		return pos3;
	}
	
	public void setPos3(Vector3D pos3) {
		this.pos3 = pos3;
	}
	
	public Vector3D getPos4() {
		return pos4;
	}
	
	public void setPos4(Vector3D pos4) {
		this.pos4 = pos4;
	}
	
}
