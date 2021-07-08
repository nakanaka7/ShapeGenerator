package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogColor.LIGHT_PURPLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;

public class ShapeCommandHandler implements SgSubCommandHandler {
	private Map<SelectionShape, SelectionStrategy> strategyMap;
	private BranchCommandHelp cmdHelp;
	
	public ShapeCommandHandler(Map<SelectionShape, SelectionStrategy> strategyMap) {
		this.strategyMap = strategyMap;
		String desc = "Set selection shape";
		String usage = "/sg shape <shape>";
		this.cmdHelp = new BranchCommandHelp("shape", desc, usage);
	}

	@Override
	public String getLabel() {
		return "shape";
	}
	
	@Override
	public BranchCommandHelp getCommandHelp() {
		return this.cmdHelp;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: " + this.cmdHelp.getUsage());
			return;
		}
		SelectionShape shape;
		try{
			shape = SelectionShape.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Invalid shape");
			return;
		}
		SelectionStrategy selStrategy = this.strategyMap.get(shape);
		if(selStrategy == null) {
			logger.print(LogColor.RED + "Unsupported shape");
			return;
		}	
		SelectionShape original = player.getSelectionShape();
		if(shape == original) {
			logger.print(LogColor.YELLOW + "Already set : Nothing to change");
			return;
		}else {
			player.setSelectionShape(shape);
			RegionBuildingData regionData = selStrategy.newRegionBuildingData();
			SelectionBuildingData selData = new SelectionBuildingData(player.getWorld(), regionData);
			player.setSelectionBuildingData(selData);
			logger.print("Set the shape -> " + LIGHT_PURPLE + shape);
			return;
		}
	}
	
	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return this.strategyMap.keySet().stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
		}else {
			return new ArrayList<>();
		}
	}

}
