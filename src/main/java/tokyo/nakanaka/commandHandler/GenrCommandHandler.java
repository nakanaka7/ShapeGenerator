package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.player.User;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.Selection;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;

public class GenrCommandHandler implements CommandHandler{
	private BlockCommandArgument blockArg;
	private SelectionStrategySource selStraSource;
	
	public GenrCommandHandler(BlockCommandArgument blockArg, SelectionStrategySource selStraSource) {
		this.blockArg = blockArg;
		this.selStraSource = selStraSource;
	}

	@Override
	public String getLabel() {
		return "genr";
	}
	
	@Override
	public String getDescription() {
		return "Generate blocks in the selection";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.REQUIRED, "block", "The block to generate"));
		return list;
	}

	@Override
	public boolean onCommand(User user, String[] args) {
		Logger logger = user.getLogger();
		if(args.length != 1) {
			return false;
		}
		SelectionBuildingData selData = user.getSelectionBuildingData();
		RegionBuildingData regionData = selData.getRegionData();
		SelectionStrategy selStrategy = this.selStraSource.get(user.getSelectionShape());
		BoundRegion3D region;
		try {
			region = selStrategy.buildBoundRegion3D(regionData);
		}catch(IllegalStateException e) {
			logger.print(LogDesignColor.ERROR + "Incomplete selection");
			return true;
		}
		Vector3D offset = selData.getOffset();
		if(offset == null) {
			offset = regionData.getVector3D(selStrategy.getDefaultOffsetLabel());
		}
		Selection sel = new Selection(selData.getWorld(), region, offset);
		Block block;
		try {
			block = this.blockArg.onParsing(args[0]);
		}catch(IllegalArgumentException e) {
			logger.print(LogDesignColor.ERROR + "Invalid block specification");
			return true;
		}
		GenerateCommand generateCmd = new GenerateCommand(sel, block, user.getBlockPhysics());	
		try {
			generateCmd.execute();
		}catch(IllegalArgumentException e) {
			logger.print(LogDesignColor.ERROR + "Unsettable block");
			return true;
		}
		logger.print(LogDesignColor.NORMAL + "Generated block(s)");
		user.getUndoCommandManager().add(generateCmd);
		return true;
	}
	
	@Override
	public List<String> onTabComplete(User user, String[] args) {
		if(args.length == 1) {
			return this.blockArg.onTabComplete(args[0]);
		}else {
			return new ArrayList<>();
		}
	}

}
