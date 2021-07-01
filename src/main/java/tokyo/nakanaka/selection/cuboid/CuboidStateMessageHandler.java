package tokyo.nakanaka.selection.cuboid;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuilder;
import tokyo.nakanaka.selection.RegionStateMessageHandler;

@Deprecated
public class CuboidStateMessageHandler implements RegionStateMessageHandler{
	
	@Override
	public List<Pair<String, String>> onMessage(RegionBuilder builder) {
		List<Pair<String, String>> list = new ArrayList<>();
		CuboidRegionBuilder cuboidBuilder = (CuboidRegionBuilder)builder;
		Vector3D pos1 = cuboidBuilder.getPos1();
		list.add(new Pair<>("pos1", pos1.toString()));
		Vector3D pos2 = cuboidBuilder.getPos2();
		list.add(new Pair<>("pos2", pos2.toString()));
		if(pos1 != null && pos2 != null) {
			double width = Math.abs(pos2.getX() - pos1.getX());
			double height = Math.abs(pos2.getY() - pos1.getY());
			double length = Math.abs(pos2.getZ() - pos1.getZ());
			list.add(new Pair<>("width", String.valueOf(width)));
			list.add(new Pair<>("height", String.valueOf(height)));
			list.add(new Pair<>("length", String.valueOf(length)));
		}
		return list;
	}

}
