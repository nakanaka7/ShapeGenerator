package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;

public class ShapeCommandHandler implements BranchCommandHandler {
	private SelectionStrategySource selStraSource;
	private BranchCommandHelp cmdHelp;
	
	public ShapeCommandHandler(SelectionStrategySource selStraSource) {
		this.selStraSource = selStraSource;
		SelectionShape[] shapes = SelectionShape.values();
		String[] shapeStrs = new String[shapes.length];
		for(int i = 0; i < shapes.length; i++) {
			shapeStrs[i] = shapes[i].toString().toLowerCase();
		}
		this.cmdHelp = new BranchCommandHelp.Builder("shape")
				.description("Set selection shape")
				.addParameter(ParameterType.REQUIRED, shapeStrs)
				.build();
	}

	@Override
	public String getLabel() {
		return "shape";
	}
	
	@Override
	public String getDescription() {
		return "Set selection shape";
	}
	
	@Override
	public BranchCommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: " + "/sg " + this.cmdHelp.getUsage());
			return;
		}
		SelectionShape shape;
		try{
			shape = SelectionShape.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Invalid shape");
			return;
		}
		SelectionStrategy selStrategy = this.selStraSource.get(shape);
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
			logger.print(LogColor.DARK_AQUA + "Set the shape" + LogColor.RESET + " -> " + LogColor.GREEN + shape);
			new SelectionMessenger().printClickDescription(logger, selStrategy);
			return;
		}
	}
	
	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return this.selStraSource.getShapeList().stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
		}else {
			return new ArrayList<>();
		}
	}

}
