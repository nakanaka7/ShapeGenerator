package tokyo.nakanaka.selection.cuboid;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.CuboidRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelSubCommandHandler;
import tokyo.nakanaka.selection.SelectionStrategy;
import tokyo.nakanaka.selection.RegionBuildingData.DataType;

public class CuboidSelectionStrategy implements SelectionStrategy{
	private LengthCalculator lengthCalc = new LengthCalculator();
	
	@Override
	public RegionBuildingData newRegionBuildingData() {
		return new RegionBuildingData.Builder()
				.addDataTag("pos1", DataType.VECTOR3D)
				.addDataTag("pos2", DataType.VECTOR3D)
				.addDataTag("width", DataType.DOUBLE)
				.addDataTag("height", DataType.DOUBLE)
				.addDataTag("length", DataType.DOUBLE)
				.build();
	}
	
	@Override
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D pos1 = blockPos.toVector3D();
		Vector3D pos2 = data.getVector3D("pos2");
		if(pos2 == null) {
			data.putVector3D("pos1", pos1);
			return;
		}
		this.adjustPos1Pos2(data);
	}

	@Override
	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D pos2 = blockPos.toVector3D();
		Vector3D pos1 = data.getVector3D("pos1");
		if(pos1 == null) {
			data.putVector3D("pos2", pos2);
			return;
		}
		this.adjustPos1Pos2(data);
	}

	//used in onLeftClickBlock(), onRightClickBlock() 
	private void adjustPos1Pos2(RegionBuildingData data) {
		Vector3D pos1 = data.getVector3D("pos1");
		Vector3D pos2 = data.getVector3D("pos2");
		if(pos1.getX() - pos2.getX() >= 0) {
			pos1 = pos1.add(new Vector3D(0.5, 0, 0));
			pos2 = pos2.negate(new Vector3D(0.5, 0, 0));
		}else {
			pos1 = pos1.negate(new Vector3D(0.5, 0, 0));
			pos2 = pos2.add(new Vector3D(0.5, 0, 0));
		}
		if(pos1.getY() - pos2.getY() >= 0) {
			pos1 = pos1.add(new Vector3D(0, 0.5, 0));
			pos2 = pos2.negate(new Vector3D(0, 0.5, 0));
		}else {
			pos1 = pos1.negate(new Vector3D(0, 0.5, 0));
			pos2 = pos2.add(new Vector3D(0, 0.5, 0));
		}
		if(pos1.getZ() - pos2.getZ() >= 0) {
			pos1 = pos1.add(new Vector3D(0, 0, 0.5));
			pos2 = pos2.negate(new Vector3D(0, 0, 0.5));
		}else {
			pos1 = pos1.negate(new Vector3D(0, 0, 0.5));
			pos2 = pos2.add(new Vector3D(0, 0, 0.5));
		}
		data.putVector3D("pos1", pos1);
		data.putVector3D("pos2", pos2);
		data.putDouble("width", this.lengthCalc.calcWidth(pos1, pos2));
		data.putDouble("height", this.lengthCalc.calcHeight(pos1, pos2));
		data.putDouble("length", this.lengthCalc.calcLength(pos1, pos2));
	}
	
	@Override
	public String getDefaultOffsetLabel() {
		return "pos1";
	}

	@Override
	public List<SelSubCommandHandler> getSelSubCommandHandlers() {
		return Arrays.asList(new Pos1CommandHandler(), new Pos2CommandHandler());
	}

	@Override
	public BoundRegion3D buildBoundRegion3D(RegionBuildingData data) {
		Vector3D pos1 = data.getVector3D("pos1");
		Vector3D pos2 = data.getVector3D("pos2");
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
