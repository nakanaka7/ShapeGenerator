package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.RootCommandHelp;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
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

public class SelCommandHandler implements BranchCommandHandler {
	private SelectionStrategySource selStraSource;
	private ResetCommandHandler resetCmdHandler;
	private OffsetCommandHandler offsetCmdHandler = new OffsetCommandHandler();
	
	public SelCommandHandler(SelectionStrategySource selStraSource) {
		this.selStraSource = selStraSource;
		this.resetCmdHandler = new ResetCommandHandler(selStraSource);
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
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		return list;
	}

	@Override
	public CommandHelp getCommandHelp(Player player) {
		RootCommandHelp cmdHelp = new RootCommandHelp.Builder("sel")
				.description("Specify the selection")
				.build();
		List<String> subLabels = this.getSubCommandLabels(player);
		for(String subLabel : subLabels) {
			cmdHelp.register(this.getSubCommandHelp(player, subLabel));
		}
		return cmdHelp;
	}
	
	public List<String> getSubCommandLabels(Player player){
		List<String> list = new ArrayList<>();
		list.add("reset");
		list.add("offset");
		SelectionShape shape = player.getSelectionShape();
		SelectionStrategy strategy = this.selStraSource.get(shape);
		List<SelSubCommandHandler> cmdHandlerList = strategy.getSelSubCommandHandlers();
		for(SelSubCommandHandler cmdHandler : cmdHandlerList) {
			list.add(cmdHandler.getLabel());
		}
		return list;
	}
	
	public BranchCommandHelp getSubCommandHelp(Player player, String label) {
		if(label.equals("reset")) {
			return this.resetCmdHandler.getCommandHelp();
		}else if(label.equals("offset")) {
			return this.offsetCmdHandler.getCommandHelp();
		}else {
			SelectionShape shape = player.getSelectionShape();
			SelectionStrategy strategy = this.selStraSource.get(shape);
			List<SelSubCommandHandler> cmdHandlerList = strategy.getSelSubCommandHandlers();
			for(SelSubCommandHandler e : cmdHandlerList) {
				if(e.getLabel().equals(label)) {
					return e.getCommandHelp();
				}
			}
			return null;
		}
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length == 0) {
			logger.print(LogColor.RED + "Empty sub command");
			return;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		World world = player.getWorld();
		BlockVector3D playerPos = new BlockVector3D(player.getX(), player.getY(), player.getZ());
		SelectionBuildingData selData = player.getSelectionBuildingData();
		SelectionShape shape = player.getSelectionShape();
		SelectionStrategy strategy = this.selStraSource.get(shape);
		String defaultOffsetLabel = strategy.getDefaultOffsetLabel();
		SelectionMessenger selMessenger = new SelectionMessenger();
		if(label.equals("reset")){
			this.resetCmdHandler.onCommand(player, shiftArgs);
			return;
		}else if(label.equals("offset")) {
			this.offsetCmdHandler.onCommand(player, shiftArgs);
			return;
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
				if(success) {
					selMessenger.printSelection(player.getLogger(), shape, selData, defaultOffsetLabel);
					return;
				}
			}
		}
		//help
		return;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		SelectionShape shape = player.getSelectionShape();
		List<SelSubCommandHandler> cmdHandlerList = this.selStraSource.get(shape).getSelSubCommandHandlers();
		if(args.length == 1) {
			List<String> list = new ArrayList<>();
			for(SelSubCommandHandler handler : cmdHandlerList) {
				list.add(handler.getLabel());
			}
			list.add("reset");
			list.add("offset");
			return list;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		if(label.equals("reset")) {
			return this.resetCmdHandler.onTabComplete(player, shiftArgs);
		}else if(label.equals("offset")) {
			return this.offsetCmdHandler.onTabComplete(player, shiftArgs);
		}
		for(SelSubCommandHandler handler : cmdHandlerList) {
			if(handler.getLabel().equals(label)) {
				return handler.onTabComplete(shiftArgs);
			}
		}
		return new ArrayList<>();
	}

}
