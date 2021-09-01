package tokyo.nakanaka.shapeGenerator.selSubCommandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.shapeGenerator.SelectionShapeNew;
import tokyo.nakanaka.shapeGenerator.Utils;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.SgSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class ResetCommandHandler implements SgSubCommandHandler {
	private SelectionStrategySource selStraSource;
	private SelectionMessenger selMessenger = new SelectionMessenger();
	
	public ResetCommandHandler(SelectionStrategySource selStraSource) {
		this.selStraSource = selStraSource;
	}
	
	public void onCommand(UserData userData, Player player, String[] args) {
		Logger logger = userData.getLogger();
		if(args.length != 0) {
			logger.print(LogColor.RED + "Usage: " + "/sg sel reset");
			return;
		}
		World world = userData.getWorld();
		SelectionShapeNew shape = userData.getSelectionShapeNew();
		SelectionStrategy strategy = this.selStraSource.get(shape);
		SelectionBuildingData newSelData = new SelectionBuildingData(world, strategy.newRegionBuildingData());
		userData.setSelectionBuildingData(newSelData);
		String defaultOffsetLabel = strategy.defaultOffsetKey();
		this.selMessenger.printSelection(logger, shape, newSelData, defaultOffsetLabel);
		List<String> lines = Utils.getSelectionMessageLines(userData.getSelectionData());
		for(String line : lines) {
			player.print(line);
		}
	}
	
	public List<String> onTabComplete(UserData user, Player player, String[] args) {
		return new ArrayList<>();
	}

}
