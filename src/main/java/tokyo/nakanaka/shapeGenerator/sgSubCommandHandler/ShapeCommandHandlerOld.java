package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg shape" command
 */
public class ShapeCommandHandlerOld implements SgSubCommandHandler {
	private SelectionStrategySource selStraSource;
	
	public ShapeCommandHandlerOld(SelectionStrategySource selStraSource) {
		this.selStraSource = selStraSource;
		SelectionShape[] shapes = SelectionShape.values();
		String[] shapeStrs = new String[shapes.length];
		for(int i = 0; i < shapes.length; i++) {
			shapeStrs[i] = shapes[i].toString().toLowerCase();
		}
	}

	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		if(args.length != 1) {
			player.print(LogColor.RED + "Usage: /sg shape <type>");
			return;
		}
		SelectionShape shape;
		try{
			shape = SelectionShape.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(LogDesignColor.ERROR + "Invalid shape");
			return;
		}
		SelectionStrategy selStrategy = this.selStraSource.get(shape);
		if(selStrategy == null) {
			player.print(LogDesignColor.ERROR + "Unsupported shape");
			return;
		}	
		SelectionShape original = userData.getSelectionShape();
		if(shape == original) {
			player.print(LogDesignColor.ERROR + "Already set : Nothing to change");
			return;
		}else {
			userData.setSelectionShape(shape);
			RegionBuildingData regionData = selStrategy.newRegionBuildingData();
			SelectionBuildingData selData = new SelectionBuildingData(userData.getWorld(), regionData);
			userData.setSelectionBuildingData(selData);
			player.print(LogDesignColor.NORMAL + "Set the shape -> " + shape);
			new SelectionMessenger().printClickDescription(player, selStrategy);
			return;
		}
	}
	
	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		if(args.length == 1) {
			return this.selStraSource.getShapeList().stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
		}else {
			return new ArrayList<>();
		}
	}

}
