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
import tokyo.nakanaka.selection.DefaultOffsetHandler;
import tokyo.nakanaka.selection.RegionBuilder;
import tokyo.nakanaka.selection.RegionStateMessageHandler;
import tokyo.nakanaka.selection.SelSubCommandHandler;
import tokyo.nakanaka.world.World;

@Deprecated
public class CuboidSelectionBuilder extends AbstractSelectionBuilder{
	private RegionBuilder regionBuilder = new CuboidRegionBuilder();
	private ClickBlockHandler clickHandler = new CuboidClickBlockHandler();
	private Map<String, SelSubCommandHandler> cmdMap = new HashMap<>();
	private DefaultOffsetHandler offsetHandler = new CuboidDefaultOffsetHandler();
	private RegionStateMessageHandler msgHandler = new CuboidStateMessageHandler();
	
	public CuboidSelectionBuilder(World world) {
		super(world);
		this.cmdMap.put("pos1", new Pos1CommandHandler());
		this.cmdMap.put("pos2", new Pos2CommandHandler());
	}
	
	@Override
	public void onLeftClickBlock(Logger logger, BlockVector3D blockPos) {
		this.clickHandler.onLeftClickBlock(this.regionBuilder, logger, blockPos);
	}

	@Override
	public void onRightClickBlock(Logger logger, BlockVector3D blockPos) {
		this.clickHandler.onRightClickBlock(this.regionBuilder, logger, blockPos);
	}
	
	@Override
	public boolean onRegionCommand(Logger logger, BlockVector3D playerPos, String label, String[] args) {
		SelSubCommandHandler handler = this.cmdMap.get(label);
		if(handler == null) {
			return false;
		}
		return handler.onCommand(this.regionBuilder, logger, playerPos, args);
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
		return this.offsetHandler
				.getDefaultOffsetLabel();
	}

	@Override
	public Vector3D getDefaultOffset() {
		return this.offsetHandler
				.getDefaultOffset(this.regionBuilder);
	}
	
	@Override
	public BoundRegion3D buildRegion() {
		return this.regionBuilder.buildRegion();
	}

}
