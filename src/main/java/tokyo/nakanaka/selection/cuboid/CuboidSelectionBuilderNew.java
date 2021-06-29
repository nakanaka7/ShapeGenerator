package tokyo.nakanaka.selection.cuboid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.selection.AbstractSelectionBuilder;
import tokyo.nakanaka.selection.ClickBlockHandler;
import tokyo.nakanaka.selection.RegionBuilder;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.RegionBuildingData.DataType;
import tokyo.nakanaka.selection.RegionStateMessageHandler;
import tokyo.nakanaka.selection.SelSubCommandHandler;
import tokyo.nakanaka.world.World;

public class CuboidSelectionBuilderNew extends AbstractSelectionBuilder {
	private RegionBuilder regionBuilder = new CuboidRegionBuilder();
	private RegionBuildingData data;
	private ClickBlockHandler clickHandler = new CuboidClickBlockHandler();
	private Map<String, SelSubCommandHandler> cmdMap = new HashMap<>();
	private RegionStateMessageHandler msgHandler = new CuboidStateMessageHandler();
	
	public CuboidSelectionBuilderNew(World world) {
		super(world);
		this.data = new RegionBuildingData.Builder()
				.addDataTag("pos1", DataType.VECTOR3D)
				.addDataTag("pos2", DataType.VECTOR3D)
				.addDataTag("width", DataType.DOUBLE)
				.addDataTag("height", DataType.DOUBLE)
				.addDataTag("length", DataType.DOUBLE)
				.build();
		this.cmdMap.put("pos1", new Pos1CommandHandler());
		this.cmdMap.put("pos2", new Pos2CommandHandler());
	}
	
	@Override
	public void onLeftClickBlock(Logger logger, BlockVector3D blockPos) {
		this.clickHandler.onLeftClickBlock(this.data, logger, blockPos);
	}

	@Override
	public void onRightClickBlock(Logger logger, BlockVector3D blockPos) {
		this.clickHandler.onRightClickBlock(this.data, logger, blockPos);
	}
	
	@Override
	public boolean onRegionCommand(Logger logger, BlockVector3D playerPos, String label, String[] args) {
		SelSubCommandHandler handler = this.cmdMap.get(label);
		if(handler == null) {
			return false;
		}
		return handler.onCommand(this.data, logger, playerPos, args);
	}

	@Override
	public List<String> getRegionCommandLabelList() {
		return new ArrayList<>(this.cmdMap.keySet());
	}
	
	@Override
	public List<String> onRegionCommandTabComplete(String label, String[] args) {
		SelSubCommandHandler handler = this.cmdMap.get(label);
		if(handler == null) {
			return new ArrayList<>();
		}
		return handler.onTabComplete(args);
	}
	
	@Override
	public List<String> getRegionStateLines() {
		List<Pair<String, String>> pairList = 
				this.msgHandler
				.onMessage(this.regionBuilder);
		List<String> list = new ArrayList<>();
		for(Pair<String, String> pair : pairList) {
			list.add(pair.getFirst() + ": " + pair.getSecond());
		}
		return list;
	}
	
	@Override
	public String getDefaultOffsetName() {
		return "pos1";
	}

	@Override
	public Vector3D getDefaultOffset() {
		return this.data.getVector3D(this.getDefaultOffsetName());
	}
	
	@Override
	public BoundRegion3D buildRegion() {
		return this.regionBuilder.buildRegion();
	}

}
