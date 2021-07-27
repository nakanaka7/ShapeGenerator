package tokyo.nakanaka.selection.selSubCommandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commadHelp.BranchCommandHelpOld;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commandHandler.CommandHandler;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.world.World;

public class ResetCommandHandler implements CommandHandler {
	private SelectionStrategySource selStraSource;
	private SelectionMessenger selMessenger = new SelectionMessenger();
	private BranchCommandHelpOld cmdHelp;
	
	public ResetCommandHandler(SelectionStrategySource selStraSource) {
		this.selStraSource = selStraSource;
		this.cmdHelp = new BranchCommandHelpOld.Builder("reset")
				.description("Reset the selection")
				.build();
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

	public BranchCommandHelpOld getCommandHelp() {
		return this.cmdHelp;
	}

	public boolean onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 0) {
			logger.print(LogColor.RED + "Usage: " + "/sg sel " + this.cmdHelp.getUsage());
			return false;
		}
		World world = player.getWorld();
		SelectionShape shape = player.getSelectionShape();
		SelectionStrategy strategy = this.selStraSource.get(shape);
		SelectionBuildingData newSelData = new SelectionBuildingData(world, strategy.newRegionBuildingData());
		player.setSelectionBuildingData(newSelData);
		String defaultOffsetLabel = strategy.getDefaultOffsetLabel();
		this.selMessenger.printSelection(logger, shape, newSelData, defaultOffsetLabel);
		return true;
	}
	
	public List<String> onTabComplete(Player player, String[] args) {
		return new ArrayList<>();
	}

}
