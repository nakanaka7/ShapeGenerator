package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.Selection;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;

public class GenrCommandHandler implements SgSubCommandHandler{
	private BlockCommandArgument blockArg;
	private Map<SelectionShape, SelectionStrategy> strategyMap;
	private BranchCommandHelp cmdHelp;
	
	public GenrCommandHandler(BlockCommandArgument blockArg, Map<SelectionShape, SelectionStrategy> strategyMap) {
		this.blockArg = blockArg;
		this.strategyMap = strategyMap;
		String desc = "Generate blocks in the selection";
		String usage = "/sg genr <block>";
		this.cmdHelp = new BranchCommandHelp("genr", desc, usage);
		this.cmdHelp = new BranchCommandHelp.Builder("genr").description(desc)
				.addParameter(ParameterType.REQUIRED, "block")
				.build();
	}

	@Override
	public String getLabel() {
		return "genr";
	}
	
	@Override
	public BranchCommandHelp getCommandHelp() {
		return this.cmdHelp;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: "+ "/sg " + this.cmdHelp.getUsageNew());
			return;
		}
		SelectionBuildingData selData = player.getSelectionBuildingData();
		RegionBuildingData regionData = selData.getRegionData();
		SelectionStrategy selStrategy = this.strategyMap.get(player.getSelectionShape());
		BoundRegion3D region;
		try {
			region = selStrategy.buildBoundRegion3D(regionData);
		}catch(IllegalStateException e) {
			logger.print(LogColor.RED + "Incomplete selection");
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
			logger.print(LogColor.RED + "Invalid block specification");
			return;
		}
		GenerateCommand generateCmd = new GenerateCommand(sel, block, player.getBlockPhysics());	
		try {
			generateCmd.execute();
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Unsettable block");
			return;
		}
		logger.print("Generated block(s)");
		player.getUndoCommandManager().add(generateCmd);
		return;
	}
	
	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return this.blockArg.onTabComplete(args[0]);
		}else {
			return new ArrayList<>();
		}
	}

}
