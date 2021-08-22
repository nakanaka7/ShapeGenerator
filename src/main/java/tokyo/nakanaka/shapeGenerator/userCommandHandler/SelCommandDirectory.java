package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.World;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selSubCommandHandler.OffsetCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.ResetCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.user.UserOld;

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
	public List<CommandEntry> getSubList(UserOld user) {
		List<CommandEntry> handlerList = new ArrayList<>();
		handlerList.add(new ResetCommandHandler(this.selStraSource));
		handlerList.add(new OffsetCommandHandler());
		SelectionShape shape = user.getSelectionShape();
		SelectionStrategy strategy = this.selStraSource.get(shape);
		List<SelSubCommandHandler> cmdHandlerList = strategy.getSelSubCommandHandlers();
		for(SelSubCommandHandler selSubCmdHandler : cmdHandlerList) {
			UserCommandHandler cmdHandler = new UserCommandHandler() {
				@Override
				public String getLabel() {
					return selSubCmdHandler.getLabel();
				}
	
				@Override
				public void onCommand(UserOld user, String[] args) {
					World world = user.getWorld();
					BlockVector3D playerPos = new BlockVector3D(user.getX(), user.getY(), user.getZ());
					SelectionBuildingData selData = user.getSelectionBuildingData();
					if(!world.equals(selData.getWorld())) {
						SelectionBuildingData newSelData = new SelectionBuildingData(world, strategy.newRegionBuildingData());
						user.setSelectionBuildingData(newSelData);	
					}
					RegionBuildingData regionData = selData.getRegionData();
					selSubCmdHandler.onCommand(regionData, user.getLogger(), playerPos, args);
					new SelectionMessenger().printSelection(user.getLogger(), shape, selData, strategy.getDefaultOffsetLabel());
				}

				@Override
				public List<String> onTabComplete(UserOld player, String[] args) {
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
