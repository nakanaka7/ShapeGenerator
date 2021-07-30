package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selSubCommandHandler.OffsetCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.ResetCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.world.World;

public class SelCommandDirectory implements CommandDirectory {
	private SelectionStrategySource selStraSource;
	
	public SelCommandDirectory(SelectionStrategySource selStraSource) {
		this.selStraSource = selStraSource;
	}

	@Override
	public String getLabel() {
		return "sel";
	}
	
	@Override
	public String getDescription() {
		return "Specify the selection";
	}
	
	@Override
	public List<CommandEntry> getSubList(Player player) {
		List<CommandEntry> handlerList = new ArrayList<>();
		handlerList.add(new ResetCommandHandler(this.selStraSource));
		handlerList.add(new OffsetCommandHandler());
		SelectionShape shape = player.getSelectionShape();
		SelectionStrategy strategy = this.selStraSource.get(shape);
		List<SelSubCommandHandler> cmdHandlerList = strategy.getSelSubCommandHandlers();
		for(SelSubCommandHandler selSubCmdHandler : cmdHandlerList) {
			CommandHandler cmdHandler = new CommandHandler() {
				@Override
				public String getLabel() {
					return selSubCmdHandler.getLabel();
				}
	
				@Override
				public boolean onCommand(Player player, String[] args) {
					World world = player.getWorld();
					BlockVector3D playerPos = new BlockVector3D(player.getX(), player.getY(), player.getZ());
					SelectionBuildingData selData = player.getSelectionBuildingData();
					if(!world.equals(selData.getWorld())) {
						SelectionBuildingData newSelData = new SelectionBuildingData(world, strategy.newRegionBuildingData());
						player.setSelectionBuildingData(newSelData);	
					}
					RegionBuildingData regionData = selData.getRegionData();
					selSubCmdHandler.onCommand(regionData, player.getLogger(), playerPos, args);
					new SelectionMessenger().printSelection(player.getLogger(), shape, selData, strategy.getDefaultOffsetLabel());
					return true;
				}

				@Override
				public List<String> onTabComplete(Player player, String[] args) {
					return selSubCmdHandler.onTabComplete(player, args);
				}

				@Override
				public String getDescription() {
					return selSubCmdHandler.getDescription();
				}

				@Override
				public List<ParameterHelp> getParameterHelpList() {
					return selSubCmdHandler.getParameterHelpList();
				}
			};
			handlerList.add(cmdHandler);
		}
		return handlerList;
	}

}
