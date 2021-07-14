package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selSubCommandHandler.OffsetCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.ResetCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.world.World;

public class SelCommandHandlerNew {
	private SelectionStrategySource selStraSource;
	
	public SelCommandHandlerNew(SelectionStrategySource selStraSource) {
		this.selStraSource = selStraSource;
	}

	public String getLabel() {
		return "sel";
	}
	
	public List<BranchCommandHandler> getSubList(Player player) {
		List<BranchCommandHandler> handlerList = new ArrayList<>();
		handlerList.add(new ResetCommandHandler(this.selStraSource));
		handlerList.add(new OffsetCommandHandler());
		SelectionShape shape = player.getSelectionShape();
		SelectionStrategy strategy = this.selStraSource.get(shape);
		List<SelSubCommandHandler> cmdHandlerList = strategy.getSelSubCommandHandlers();
		for(SelSubCommandHandler selSubCmdHandler : cmdHandlerList) {
			BranchCommandHandler cmdHandler = new BranchCommandHandler() {
				@Override
				public String getLabel() {
					return selSubCmdHandler.getLabel();
				}

				@Override
				public CommandHelp getCommandHelp(Player player) {
					return selSubCmdHandler.getCommandHelp();
				}

				@Override
				public void onCommand(Player player, String[] args) {
					World world = player.getWorld();
					BlockVector3D playerPos = new BlockVector3D(player.getX(), player.getY(), player.getZ());
					SelectionBuildingData selData = player.getSelectionBuildingData();
					if(!world.equals(selData.getWorld())) {
						SelectionBuildingData newSelData = new SelectionBuildingData(world, strategy.newRegionBuildingData());
						player.setSelectionBuildingData(newSelData);	
					}
					RegionBuildingData regionData = selData.getRegionData();
					selSubCmdHandler.onCommand(regionData, player.getLogger(), playerPos, args);
				}

				@Override
				public List<String> onTabComplete(Player player, String[] args) {
					return selSubCmdHandler.onTabComplete(args);
				}
			};
			handlerList.add(cmdHandler);
		}
		return handlerList;
	}
	
}
