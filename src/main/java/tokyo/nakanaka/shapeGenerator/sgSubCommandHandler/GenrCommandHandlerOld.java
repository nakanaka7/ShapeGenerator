package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.Selection;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.commandHelp.GenrHelp;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg genr" command
 */
public class GenrCommandHandlerOld implements SgSubCommandHandler{
	private BlockCommandArgument blockArg;
	private SelectionStrategySource selStraSource;
	
	public GenrCommandHandlerOld(BlockCommandArgument blockArg, SelectionStrategySource selStraSource) {
		this.blockArg = blockArg;
		this.selStraSource = selStraSource;
	}
	
	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		if(args.length != 1) {
			player.print(LogColor.RED + "Usage: " +  new GenrHelp().getUsage());
			return;
		}
		SelectionBuildingData selData = userData.getSelectionBuildingData();
		RegionBuildingData regionData = selData.getRegionData();
		SelectionStrategy selStrategy = this.selStraSource.get(userData.getSelectionShape());
		BoundRegion3D region;
		try {
			region = selStrategy.buildBoundRegion3D(regionData);
		}catch(IllegalStateException e) {
			player.print(LogDesignColor.ERROR + "Incomplete selection");
			return;
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
			player.print(LogDesignColor.ERROR + "Invalid block specification");
			return;
		}
		GenerateCommand generateCmd = new GenerateCommand(sel, block, userData.getBlockPhysics());	
		try {
			generateCmd.execute();
		}catch(IllegalArgumentException e) {
			player.print(LogDesignColor.ERROR + "Unsettable block");
			return;
		}
		player.print(LogDesignColor.NORMAL + "Generated block(s)");
		userData.getUndoCommandManager().add(generateCmd);
		return;
	}
	
	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		if(args.length == 1) {
			return this.blockArg.onTabComplete(args[0]);
		}else {
			return new ArrayList<>();
		}
	}

}
