package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.Parameter;
import tokyo.nakanaka.commadHelp.Parameter.Type;
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
import tokyo.nakanaka.selection.SelectionStrategy;

public class GenrCommandHandler implements SubCommandHandler{
	private BlockCommandArgument blockArg;
	private Map<SelectionShape, SelectionStrategy> strategyMap;
	private CommandHelp help = new CommandHelp.Builder("genr")
			.description("Generate blocks in the selection")
			.addParameter(new Parameter(Type.REQUIRED, "block"), "block to generate")
			.build();
	
	private String usage = "/sg genr <block>";
	
	public GenrCommandHandler(BlockCommandArgument blockArg, Map<SelectionShape, SelectionStrategy> strategyMap) {
		this.blockArg = blockArg;
		this.strategyMap = strategyMap;
	}

	@Override
	public CommandHelp getCommandHelp() {
		return this.help;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: "+ this.usage);
			return true;
		}
		SelectionBuildingData selData = player.getSelectionBuildingData();
		RegionBuildingData regionData = selData.getRegionData();
		SelectionStrategy selStrategy = this.strategyMap.get(player.getSelectionShape());
		BoundRegion3D region;
		try {
			region = selStrategy.buildBoundRegion3D(regionData);
		}catch(IllegalStateException e) {
			player.getLogger().print(HEAD_ERROR + "Incomplete selection");
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
			player.getLogger().print(HEAD_ERROR + "Invalid block specification");
			return true;
		}
		GenerateCommand generateCmd = new GenerateCommand(sel, block, player.getBlockPhysics());	
		try {
			generateCmd.execute();
		}catch(IllegalArgumentException e) {
			player.getLogger().print(HEAD_ERROR + "Unsettable block");
			return true;
		}
		player.getUndoCommandManager().add(generateCmd);
		return true;
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
