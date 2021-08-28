package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.user.UserData;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.UserCommandHandler;

public class ResetCommandHandler implements UserCommandHandler {
	private SelectionStrategySource selStraSource;
	private SelectionMessenger selMessenger = new SelectionMessenger();
	
	public ResetCommandHandler(SelectionStrategySource selStraSource) {
		this.selStraSource = selStraSource;
	}

	@Override
	public String getLabel() {
		return "reset";
	}
	
	@Override
	public String getDescription() {
		return "Reset the selection";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		return new ArrayList<>();
	}
	
	public void onCommand(UserData userData, Player player, String[] args) {
		Logger logger = userData.getLogger();
		if(args.length != 0) {
			logger.print(LogColor.RED + "Usage: " + "/sg sel reset");
			return;
		}
		World world = userData.getWorld();
		SelectionShape shape = userData.getSelectionShape();
		SelectionStrategy strategy = this.selStraSource.get(shape);
		SelectionBuildingData newSelData = new SelectionBuildingData(world, strategy.newRegionBuildingData());
		userData.setSelectionBuildingData(newSelData);
		String defaultOffsetLabel = strategy.getDefaultOffsetLabel();
		this.selMessenger.printSelection(logger, shape, newSelData, defaultOffsetLabel);
	}
	
	public List<String> onTabComplete(UserData user, Player player, String[] args) {
		return new ArrayList<>();
	}

}
