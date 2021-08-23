package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.Selection;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.commandHelp.GenrHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class GenrCommandHandler implements UserCommandHandler{
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
	public void onCommand(UserData userData, CommandSender cmdSender, String[] args) {
		if(args.length != 1) {
			cmdSender.print(LogColor.RED + "Usage: " +  new GenrHelp().getUsage());
			return;
		}
		SelectionBuildingData selData = userData.getSelectionBuildingData();
		RegionBuildingData regionData = selData.getRegionData();
		SelectionStrategy selStrategy = this.selStraSource.get(userData.getSelectionShape());
		BoundRegion3D region;
		try {
			region = selStrategy.buildBoundRegion3D(regionData);
		}catch(IllegalStateException e) {
			cmdSender.print(LogDesignColor.ERROR + "Incomplete selection");
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
			cmdSender.print(LogDesignColor.ERROR + "Invalid block specification");
			return;
		}
		GenerateCommand generateCmd = new GenerateCommand(sel, block, userData.getBlockPhysics());	
		try {
			generateCmd.execute();
		}catch(IllegalArgumentException e) {
			cmdSender.print(LogDesignColor.ERROR + "Unsettable block");
			return;
		}
		cmdSender.print(LogDesignColor.NORMAL + "Generated block(s)");
		userData.getUndoCommandManager().add(generateCmd);
		return;
	}
	
	@Override
	public List<String> onTabComplete(UserData userData, CommandSender cmdSender, String[] args) {
		if(args.length == 1) {
			return this.blockArg.onTabComplete(args[0]);
		}else {
			return new ArrayList<>();
		}
	}

}
