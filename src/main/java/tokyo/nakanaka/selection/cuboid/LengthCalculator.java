package tokyo.nakanaka.selection.cuboid;

import tokyo.nakanaka.math.Vector3D;

public class LengthCalculator {
	public double calcWidth(Vector3D pos1, Vector3D pos2) {
		return Math.abs(pos1.getX() - pos2.getX());
	}
	
	public double calcHeight(Vector3D pos1, Vector3D pos2) {
		return Math.abs(pos1.getY() - pos2.getY());
	}
	
	public double calcLength(Vector3D pos1, Vector3D pos2) {
		return Math.abs(pos1.getZ() - pos2.getZ());
	}
	
}
