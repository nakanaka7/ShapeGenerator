package tokyo.nakanaka.shapeGenerator.selectionStrategy.cuboid;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.math.Vector3D;

public class CuboidSelectionMessenger {
	public List<String> getMessageLines(CuboidRegionData cuboidRegData){
		List<String> lines = new ArrayList<>();
		String pos1Line = LogColor.GOLD + "pos1: ";
		Vector3D pos1 = cuboidRegData.getPos1();
		if(pos1 != null) {
			pos1Line += pos1.getX() + " / " + pos1.getY() + " / " + pos1.getZ();
		}
		lines.add(pos1Line);
		String pos2Line = LogColor.GOLD + "pos2: ";
		Vector3D pos2 = cuboidRegData.getPos2();
		if(pos2 != null) {
			pos2Line += pos2.getX() + " / " + pos2.getY() + " / " + pos2.getZ();
		}
		lines.add(pos2Line);
		return lines;
	}
	
}
