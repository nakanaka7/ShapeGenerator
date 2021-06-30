package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelSubCommandHandler;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessengerNew;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.SelectionStrategy;
import tokyo.nakanaka.world.World;

public class SelCommandHandlerNew implements SubCommandHandler{
	private Map<SelectionShape, SelectionStrategy> strategyMap = new HashMap<>();
	private static final String RESET = "reset";
	private CommandHelp help = new CommandHelp.Builder("sel")
			.description("Specify a selection/ See each shape help")
			.build();
	
	public SelCommandHandlerNew(Map<SelectionShape, SelectionStrategy> strategyMap) {
		this.strategyMap = strategyMap;
	}

	@Override
	public CommandHelp getCommandHelp() {
		return this.help;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 0) {
			return false;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		World world = player.getWorld();
		BlockVector3D playerPos = new BlockVector3D(player.getX(), player.getY(), player.getZ());
		SelectionBuildingData selData = player.getSelectionBuildingData();
		SelectionShape shape = player.getSelectionShape();
		SelectionStrategy strategy = this.strategyMap.get(shape);
		SelectionMessengerNew selMessenger = new SelectionMessengerNew();
		if(label.equals(RESET)){
			SelectionBuildingData newSelData = new SelectionBuildingData(world, strategy.newRegionBuildingData());
			player.setSelectionBuildingData(newSelData);
			selMessenger.sendMessage(player.getLogger(), newSelData);
			return true;
		}
		if(!world.equals(selData.getWorld())) {
			SelectionBuildingData newSelData = new SelectionBuildingData(world, strategy.newRegionBuildingData());
			player.setSelectionBuildingData(newSelData);	
		}
		RegionBuildingData regionData = selData.getRegionData();
		List<SelSubCommandHandler> cmdHandlerList = strategy.getSelSubCommandHandlers();
		for(SelSubCommandHandler cmdHandler : cmdHandlerList) {
			if(cmdHandler.getLabel().equals(label)) {
				boolean success = cmdHandler.onCommand(regionData, player.getLogger(), playerPos, shiftArgs);
				if(!success) {
					//help
					return true;
				}
				selMessenger.sendMessage(player.getLogger(), selData);
				return true;
			}
		}
		//help
		return true;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		SelectionShape shape = player.getSelectionShape();
		List<SelSubCommandHandler> cmdHandlerList = this.strategyMap.get(shape).getSelSubCommandHandlers();
		if(args.length == 1) {
			List<String> list = new ArrayList<>();
			for(SelSubCommandHandler handler : cmdHandlerList) {
				list.add(handler.getLabel());
			}
			list.add(RESET);
			return list;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		for(SelSubCommandHandler handler : cmdHandlerList) {
			if(handler.getLabel().equals(label)) {
				return handler.onTabComplete(shiftArgs);
			}
		}
		return new ArrayList<>();
	}
}
