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
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.DelCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.GenrCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.HelpCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MaxXCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MaxYCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MaxZCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MinXCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MinYCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MinZCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MirrorCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.PhyCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.RedoCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.RotCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.ScaleCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.ShapeCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.ShiftCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.UndoCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.SgSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.WandCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Main class for the project. 
 */
public class Main {
	private SelectionStrategySource selStrtgSource;
	private Map<String, SgSubCommandHandler> userCmdHandlerMap = new HashMap<>();
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
	/**
	 * Handles "/sg" command
	 * @param cmdSender a command sender
	 * @param args arguments of the command line
	 */
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
		SgSubCommandHandler userCmdHandler = this.userCmdHandlerMap.get(subLabel);
		if(userCmdHandler == null) {
			cmdSender.print(LogColor.RED + "Unknown subcommand");
			cmdSender.print(LogColor.RED + "Run \"" + new HelpHelp().getUsage() + "\" for help");
			return;
		}
		UUID uid = player.getUniqueID();
		UserData userData = this.userDataMap.get(uid);
		if(userData == null) {
			userData = new UserData();
			this.userDataMap.put(uid, userData);
		}
		userCmdHandler.onCommand(userData, player, subArgs);
	}
	/**
	 * Get a list for tab complete of "/sg" command
	 * @param cmdSender a command sender
	 * @param args arguments of the command line
	 * @return a list for tab complete of "/sg" command
	 */
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
		SgSubCommandHandler userCmdHandler = this.userCmdHandlerMap.get(subLabel);
		if(userCmdHandler != null) {
			UUID uid = player.getUniqueID();
			UserData userData = this.userDataMap.get(uid);
			if(userData == null) {
				userData = new UserData();
				this.userDataMap.put(uid, userData);
			}
			return userCmdHandler.onTabComplete(userData, player, subArgs);
		}
		return List.of();
	}
	/**
	 * Handles a click block event
	 * @param evt a click block event
	 */
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
