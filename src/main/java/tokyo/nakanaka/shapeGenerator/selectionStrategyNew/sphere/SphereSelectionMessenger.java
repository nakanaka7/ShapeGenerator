package tokyo.nakanaka.shapeGenerator.selectionStrategyNew.sphere;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.math.Vector3D;

public class SphereSelectionMessenger {
	/**
	 * Create message lines for the selection
	 * @param sphereRegData the region data
	 * @return message lines for the selection
	 */
	public List<String> getMessageLines(SphereRegionData sphereRegData){
		List<String> lines = new ArrayList<>();
		String centerLine = LogColor.GOLD + "center: " + LogColor.RESET;
		Vector3D center = sphereRegData.getCenter();
		centerLine += center.getX() + " / " + center.getY() + " / " + center.getZ();
		lines.add(centerLine);
		String radiusLine = LogColor.GOLD + "radius: " + LogColor.RESET;
		double radius = sphereRegData.getRadius();
		radiusLine += radius;
		lines.add(radiusLine);
		return lines;
	}
	
}
