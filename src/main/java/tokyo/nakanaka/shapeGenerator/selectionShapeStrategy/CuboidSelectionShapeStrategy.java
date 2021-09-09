package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

public class CuboidSelectionShapeStrategy implements SelectionShapeStrategy{
	
	@Override
	public SelectionData newSelectionData(World world) {
		return new SelectionData(world, "pos1", "pos1", "pos2");
	}
	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap(){
		Map<String,  SubCommandHandler> map = new HashMap<>();
		map.put("pos1", new PosCommandHandlerNew("pos1"));
		map.put("pos2", new PosCommandHandlerNew("pos2"));
		return map;
	}
	
	@Override
	public String leftClickDescription() {
		return "Set pos1";
	}

	@Override
	public String rightClickDescription() {
		return "Set pos2";
	}

	@Override
	public void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData("pos1", blockPos.toVector3D());
	}
	
	@Override
	public void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData("pos2", blockPos.toVector3D());
	}
	
	@Override
	public Selection buildSelection(SelectionData selData) {
		BoundRegion3D boundReg;
		Vector3D pos1 = (Vector3D) selData.getExtraData("pos1");
		Vector3D pos2 = (Vector3D) selData.getExtraData("pos2");
		if(pos1 == null || pos2 == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Cuboid(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
		double ubx = Math.max(pos1.getX(), pos2.getX());
		double uby = Math.max(pos1.getY(), pos2.getY());
		double ubz = Math.max(pos1.getZ(), pos2.getZ());
		double lbx = Math.min(pos1.getX(), pos2.getX());
		double lby = Math.min(pos1.getY(), pos2.getY());
		double lbz = Math.min(pos1.getZ(), pos2.getZ());
		boundReg = new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
		return new Selection(selData.world(), boundReg, selData.getOffset());
	}
	
}
