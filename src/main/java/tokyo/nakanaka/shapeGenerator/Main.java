package tokyo.nakanaka.shapeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Item;
import tokyo.nakanaka.NamespacedID;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.commandHelp.HelpHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.DelCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.GenrCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.HelpCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.MaxXCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.MaxYCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.MaxZCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.MinXCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.MinYCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.MinZCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.MirrorCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.PhyCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.RedoCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.RotCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.ScaleCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.ShapeCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.ShiftCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.UndoCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.UserCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.WandCommandHandler;

public class Main {
	private SelectionStrategySource selStrtgSource;
	private Map<String, UserCommandHandler> userCmdHandlerMap = new HashMap<>();
	private Map<UUID, UserData> userDataMap = new HashMap<>();
	
	public Main(BlockCommandArgument blockArg, SelectionStrategySource selStrtgSource) {
		this.selStrtgSource = selStrtgSource;
		this.userCmdHandlerMap.put("help", new HelpCommandHandler());
		this.userCmdHandlerMap.put("wand", new WandCommandHandler());
		this.userCmdHandlerMap.put("shape", new ShapeCommandHandler(selStrtgSource));
		this.userCmdHandlerMap.put("genr", new GenrCommandHandler(blockArg, selStrtgSource));
		this.userCmdHandlerMap.put("phy", new PhyCommandHandler());
		this.userCmdHandlerMap.put("shift", new ShiftCommandHandler());
		this.userCmdHandlerMap.put("scale", new ScaleCommandHandler());
		this.userCmdHandlerMap.put("mirror", new MirrorCommandHandler());
		this.userCmdHandlerMap.put("rot", new RotCommandHandler());
		this.userCmdHandlerMap.put("maxx", new MaxXCommandHandler());
		this.userCmdHandlerMap.put("maxy", new MaxYCommandHandler());
		this.userCmdHandlerMap.put("maxz", new MaxZCommandHandler());
		this.userCmdHandlerMap.put("minx", new MinXCommandHandler());
		this.userCmdHandlerMap.put("miny", new MinYCommandHandler());
		this.userCmdHandlerMap.put("minz", new MinZCommandHandler());
		this.userCmdHandlerMap.put("del", new DelCommandHandler());
		this.userCmdHandlerMap.put("undo", new UndoCommandHandler());
		this.userCmdHandlerMap.put("redo", new RedoCommandHandler());
	}
		
	public void onSgCommand(CommandSender cmdSender, String[] args) {
		if(!(cmdSender instanceof Player player)) {
			cmdSender.print(LogColor.RED + "Player only command");
			return;
		}
		if(args.length == 0) {
			cmdSender.print(LogColor.RED + "Usage: /sg <subcommand>");
			cmdSender.print(LogColor.RED + "Run \"" + new HelpHelp().getUsage() + "\" for help");
			return;
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		UserCommandHandler userCmdHandler = this.userCmdHandlerMap.get(subLabel);
		if(userCmdHandler != null) {
			UUID uid = player.getUniqueID();
			UserData userData = this.userDataMap.get(uid);
			if(userData == null) {
				userData = new UserData();
				this.userDataMap.put(uid, userData);
			}
			userCmdHandler.onCommand(userData, cmdSender, subArgs);
		}
	}
	
	public List<String> onSgTabComplete(CommandSender cmdSender, String[] args) {
		if(!(cmdSender instanceof Player player)) {
			return List.of();
		}
		if(args.length == 1) {
			return 	new ArrayList<>(this.userCmdHandlerMap.keySet());
		}	
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		UserCommandHandler userCmdHandler = this.userCmdHandlerMap.get(subLabel);
		if(userCmdHandler != null) {
			UUID uid = player.getUniqueID();
			UserData userData = this.userDataMap.get(uid);
			if(userData == null) {
				userData = new UserData();
				this.userDataMap.put(uid, userData);
			}
			return userCmdHandler.onTabComplete(userData, cmdSender, subArgs);
		}
		return List.of();
	}
	
	public void onClickBlockEvent(ClickBlockEvent evt) {
		Item item = evt.getItemStack().getItem();
		if(!item.equals(new Item(new NamespacedID("minecraft", "blaze_rod")))) {
			return;
		}
		evt.cancel();
		Player player = evt.getPlayer();
		UUID uid = player.getUniqueID();
		UserData userData = this.userDataMap.get(uid);
		if(userData == null) {
			userData = new UserData();
			this.userDataMap.put(uid, userData);		
			MainFunctions.setDefaultSelection(this.selStrtgSource, userData);
		}
		userData.setLogger(player);
		SelectionShape selShape = userData.getSelectionShape();
		SelectionBuildingData selData = userData.getSelectionBuildingData();
		SelectionStrategy selStrategy = this.selStrtgSource.get(selShape);
		BlockPosition blockPos = evt.getBlockPos();
		World world = blockPos.world();
		if(!world.equals(selData.getWorld())) {
			RegionBuildingData regionData = selStrategy.newRegionBuildingData();
			selData = new SelectionBuildingData(world, regionData);
			userData.setSelectionBuildingData(selData);
		}
		RegionBuildingData regionData = selData.getRegionData();
		BlockVector3D pos = new BlockVector3D(blockPos.x(), blockPos.y(), blockPos.z());
		switch(evt.getHandType()) {
			case LEFT_HAND -> {
				selStrategy.onLeftClickBlock(regionData, player, pos);
			}
			case RIGHT_HAND -> {
				selStrategy.onRightClickBlock(regionData, player, pos);
			}
		}
		new SelectionMessenger().printSelection(player, selShape, selData, selStrategy.getDefaultOffsetLabel());
	}
	
}
