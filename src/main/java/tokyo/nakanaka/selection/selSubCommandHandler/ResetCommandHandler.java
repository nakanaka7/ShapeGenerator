package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.world.World;

public class ResetCommandHandler {
	private Map<SelectionShape, SelectionStrategy> strategyMap;
	private SelectionMessenger selMessenger = new SelectionMessenger();
	private BranchCommandHelp cmdHelp;
	
	public ResetCommandHandler(Map<SelectionShape, SelectionStrategy> strategyMap) {
		this.strategyMap = strategyMap;
		String description = "Reset the selection";
		String usage = "/reset";
		this.cmdHelp = new BranchCommandHelp("reset", description, usage);
	}
	
	public BranchCommandHelp getCommandHelp() {
		return this.cmdHelp;
	}

	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 0) {
			logger.print(LogColor.RED + "Usage: " + this.cmdHelp.getUsage());
			return;
		}
		World world = player.getWorld();
		SelectionShape shape = player.getSelectionShape();
		SelectionStrategy strategy = this.strategyMap.get(shape);
		SelectionBuildingData newSelData = new SelectionBuildingData(world, strategy.newRegionBuildingData());
		player.setSelectionBuildingData(newSelData);
		String defaultOffsetLabel = strategy.getDefaultOffsetLabel();
		this.selMessenger.sendMessage(logger, shape, newSelData, defaultOffsetLabel);
		return;
	}
	
	public List<String> onTabComplete(String[] args) {
		return new ArrayList<>();
	}
}
