package tokyo.nakanaka.selection.cuboid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.selection.AbstractSelectionBuilder;
import tokyo.nakanaka.selection.RegionBuilder;
import tokyo.nakanaka.selection.SelSubCommandHandler;
import tokyo.nakanaka.selection.SelectionHandlerFactory;
import tokyo.nakanaka.world.World;

public class CuboidSelectionBuilder extends AbstractSelectionBuilder{
	private RegionBuilder regionBuilder;
	private SelectionHandlerFactory handlerFactory;
	
	public CuboidSelectionBuilder(World world) {
		super(world);
		this.regionBuilder = new CuboidRegionBuilder();
		this.handlerFactory = new CuboidSelectionHandlerFactory();
	}
	
	@Override
	public void onLeftClickBlock(Logger logger, BlockVector3D blockPos) {
		this.handlerFactory.getClickBlockHandler().onLeftClickBlock(this.regionBuilder, logger, blockPos);
	}

	@Override
	public void onRightClickBlock(Logger logger, BlockVector3D blockPos) {
		this.handlerFactory.getClickBlockHandler().onRightClickBlock(regionBuilder, logger, blockPos);
	}
	
	@Override
	public boolean onRegionCommand(Logger logger, BlockVector3D playerPos, String label, String[] args) {
		SelSubCommandHandler handler = this.handlerFactory.getSelSubCommandHandlers().get(label);
		if(handler == null) {
			return false;
		}
		handler.onCommand(this.regionBuilder, logger, playerPos, args);
		return true;
	}

	@Override
	public List<String> getRegionCommandLabelList() {
		Map<String, SelSubCommandHandler> cmdHandlerMap = this.handlerFactory.getSelSubCommandHandlers();
		return new ArrayList<>(cmdHandlerMap.keySet());
	}
	
	@Override
	public List<String> onRegionCommandTabComplete(String label, String[] args) {
		Map<String, SelSubCommandHandler> cmdHandlerMap = this.handlerFactory.getSelSubCommandHandlers();
		SelSubCommandHandler handler = cmdHandlerMap.get(label);
		if(handler == null) {
			return new ArrayList<>();
		}
		return handler.onTabComplete(args);
	}
	
	@Override
	public List<String> getRegionStateLines() {
		List<Pair<String, String>> pairList = 
				this.handlerFactory
				.getRegionStateMessageHandler()
				.onMessage(this.regionBuilder);
		List<String> list = new ArrayList<>();
		for(Pair<String, String> pair : pairList) {
			list.add(pair.getFirst() + ": " + pair.getSecond());
		}
		return list;
	}
	
	@Override
	public String getDefaultOffsetName() {
		return this.handlerFactory
				.getDefaultOffsetHandler()
				.getDefaultOffsetLabel();
	}

	@Override
	public Vector3D getDefaultOffset() {
		return this.handlerFactory
				.getDefaultOffsetHandler()
				.getDefaultOffset(this.regionBuilder);
	}
	
	@Override
	public BoundRegion3D buildRegion() {
		return this.regionBuilder.buildRegion();
	}

}
