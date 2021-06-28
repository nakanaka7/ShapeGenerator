package tokyo.nakanaka.selection.cuboid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.selection.AbstractSelectionBuilder;
import tokyo.nakanaka.selection.SelSubCommandHandler;
import tokyo.nakanaka.selection.SelSubCommandHandlerRepository;
import tokyo.nakanaka.world.World;

public class CuboidSelectionBuilder extends AbstractSelectionBuilder{
	private CuboidRegionBuilder cuboidBuilder = new CuboidRegionBuilder();
	private CuboidClickBlockHandler clickHandler = new CuboidClickBlockHandler();
	private static SelSubCommandHandlerRepository cmdHandlerRepo = new SelSubCommandHandlerRepository(); 
	static {
		cmdHandlerRepo.register(new Pos1CommandHandler());
		cmdHandlerRepo.register(new Pos2CommandHandler());
	}
	private CuboidStateMessageHandler msgHandler = new CuboidStateMessageHandler();

	public CuboidSelectionBuilder(World world) {
		super(world);
	}
	
	@Override
	public void onLeftClickBlock(Logger logger, BlockVector3D blockPos) {
		this.clickHandler.onLeftClickBlock(this.cuboidBuilder, logger, blockPos);
	}

	@Override
	public void onRightClickBlock(Logger logger, BlockVector3D blockPos) {
		this.clickHandler.onRightClickBlock(cuboidBuilder, logger, blockPos);
	}
	
	@Override
	public boolean onRegionCommand(Logger logger, BlockVector3D playerPos, String label, String[] args) {
		SelSubCommandHandler handler = cmdHandlerRepo.findBy(label);
		if(handler == null) {
			return false;
		}
		handler.onCommand(this.cuboidBuilder, logger, playerPos, args);
		return true;
	}

	@Override
	public List<String> getRegionCommandLabelList() {
		return Arrays.asList("pos1", "pos2");
	}
	
	@Override
	public List<String> onRegionCommandTabComplete(String label, String[] args) {
		SelSubCommandHandler handler = cmdHandlerRepo.findBy(label);
		if(handler == null) {
			return new ArrayList<>();
		}
		return handler.onTabComplete(args);
	}
	
	@Override
	public List<String> getRegionStateLines() {
		List<Pair<String, String>> pairList = this.msgHandler.onMessage(this.cuboidBuilder);
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
		return this.cuboidBuilder.getPos1();
	}
	
	@Override
	public BoundRegion3D buildRegion() {
		return this.cuboidBuilder.buildRegion();
	}

}
