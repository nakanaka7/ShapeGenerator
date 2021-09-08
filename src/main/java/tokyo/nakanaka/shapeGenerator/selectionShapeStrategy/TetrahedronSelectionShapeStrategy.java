package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.max;
import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.min;

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
import tokyo.nakanaka.shapeGenerator.math.region3D.Tetrahedron;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TetrahedronRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.tetrahedronSelSubCommandHandler.Pos1CommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.tetrahedronSelSubCommandHandler.Pos2CommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.tetrahedronSelSubCommandHandler.Pos3CommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.tetrahedronSelSubCommandHandler.Pos4CommandHandler;

public class TetrahedronSelectionShapeStrategy implements SelectionShapeStrategy {

	@Override
	public RegionData newRegionData() {
		return new TetrahedronRegionData();
	}
	
	public SelectionData newSelectionData(World world) {
		return new SelectionData(world, "pos1", "pos1", "pos2", "pos3", "pos4");
	}
	
	public Selection buildSelectionNew(SelectionData selData) {
		BoundRegion3D boundReg;
		Vector3D pos1 = (Vector3D) selData.getExtraData("pos1");
		Vector3D pos2 = (Vector3D) selData.getExtraData("pos2");
		Vector3D pos3 = (Vector3D) selData.getExtraData("pos3");
		Vector3D pos4 = (Vector3D) selData.getExtraData("pos4");
		if(pos1 == null || pos2 == null || pos3 == null || pos4 == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Tetrahedron(pos1.getX(), pos1.getY(), pos1.getZ(),
				pos2.getX(), pos2.getY(), pos2.getZ(),
				pos3.getX(), pos3.getY(), pos3.getZ(),
				pos4.getX(), pos4.getY(), pos4.getZ());
		double ubx = max(pos1.getX(), pos2.getX(), pos3.getX(), pos4.getX());
		double uby = max(pos1.getY(), pos2.getY(), pos3.getY(), pos4.getY());
		double ubz = max(pos1.getZ(), pos2.getZ(), pos3.getZ(), pos4.getZ());
		double lbx = min(pos1.getX(), pos2.getX(), pos3.getX(), pos4.getX());
		double lby = min(pos1.getY(), pos2.getY(), pos3.getY(), pos4.getY());
		double lbz = min(pos1.getZ(), pos2.getZ(), pos3.getZ(), pos4.getZ());
		boundReg = new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
		return new Selection(selData.world(), boundReg, selData.getOffset());
	}
	
	@Override
	public String leftClickDescription() {
		return "Set pos1";
	}

	@Override
	public String rightClickDescription() {
		return "Set pos2, pos3, pos4";
	}
	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put("pos1", new Pos1CommandHandler());
		map.put("pos2", new Pos2CommandHandler());
		map.put("pos3", new Pos3CommandHandler());
		map.put("pos4", new Pos4CommandHandler());
		return map;
	}
	
	public Map<String, SubCommandHandler> selSubCommandHandlerMapNew(){
		Map<String,  SubCommandHandler> map = new HashMap<>();
		map.put("pos1", new PosCommandHandlerNew("pos1"));
		map.put("pos2", new PosCommandHandlerNew("pos2"));
		map.put("pos3", new PosCommandHandlerNew("pos3"));
		map.put("pos4", new PosCommandHandlerNew("pos4"));
		return map;
	}
	
	public void onLeftClickNew(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData("pos1", blockPos.toVector3D());
		selData.setExtraData("pos2", null);
		selData.setExtraData("pos3", null);
		selData.setExtraData("pos4", null);
	}
	
	public void onRightClickNew(SelectionData selData, BlockVector3D blockPos) {
		if(selData.getExtraData("pos1") == null) {
			throw new IllegalStateException();
		}
		Vector3D pos = blockPos.toVector3D();
		if(selData.getExtraData("pos2") == null) {
			selData.setExtraData("pos2", pos);
		}else if(selData.getExtraData("pos3") == null) {
			selData.setExtraData("pos3", pos);
		}else {
			selData.setExtraData("pos4", pos);
		}
	}
	
}
