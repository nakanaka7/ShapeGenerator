package tokyo.nakanaka.shapeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Item;
import tokyo.nakanaka.NamespacedID;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.shapeGenerator.commandHelp.HelpHelp;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.*;
import tokyo.nakanaka.shapeGenerator.user.User;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Main class for the project. 
 */
public class Main {
	private SelectionStrategySource selStrtgSource;
	private Map<String, SgSubCommandHandler> sgSubCmdHandlerMap = new HashMap<>();
	private Map<User, UserData> userDataMap = new HashMap<>();
	
	public Main(BlockIDListFactory blockIDListFactory, SelectionStrategySource selStrtgSource) {
		this.selStrtgSource = selStrtgSource;
		this.sgSubCmdHandlerMap.put("help", new HelpCommandHandler());
		this.sgSubCmdHandlerMap.put("wand", new WandCommandHandler());
		this.sgSubCmdHandlerMap.put("shape", new ShapeCommandHandlerNew());
		this.sgSubCmdHandlerMap.put("sel", new SelCommandHandlerNew());
		this.sgSubCmdHandlerMap.put("genr", new GenrCommandHandlerNew(blockIDListFactory));
		this.sgSubCmdHandlerMap.put("phy", new PhyCommandHandler());
		this.sgSubCmdHandlerMap.put("shift", new ShiftCommandHandler());
		this.sgSubCmdHandlerMap.put("scale", new ScaleCommandHandler());
		this.sgSubCmdHandlerMap.put("mirror", new MirrorCommandHandler());
		this.sgSubCmdHandlerMap.put("rot", new RotCommandHandler());
		this.sgSubCmdHandlerMap.put("maxx", new MaxXCommandHandler());
		this.sgSubCmdHandlerMap.put("maxy", new MaxYCommandHandler());
		this.sgSubCmdHandlerMap.put("maxz", new MaxZCommandHandler());
		this.sgSubCmdHandlerMap.put("minx", new MinXCommandHandler());
		this.sgSubCmdHandlerMap.put("miny", new MinYCommandHandler());
		this.sgSubCmdHandlerMap.put("minz", new MinZCommandHandler());
		this.sgSubCmdHandlerMap.put("del", new DelCommandHandler());
		this.sgSubCmdHandlerMap.put("undo", new UndoCommandHandler());
		this.sgSubCmdHandlerMap.put("redo", new RedoCommandHandler());
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
		SgSubCommandHandler sgSubCmdHandler = this.sgSubCmdHandlerMap.get(subLabel);
		if(sgSubCmdHandler == null) {
			cmdSender.print(LogColor.RED + "Unknown subcommand");
			cmdSender.print(LogColor.RED + "Run \"" + new HelpHelp().getUsage() + "\" for help");
			return;
		}
		UserData userData = this.prepareUserDataNew(player);
		sgSubCmdHandler.onCommand(userData, player, subArgs);
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
			return 	new ArrayList<>(this.sgSubCmdHandlerMap.keySet());
		}	
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		SgSubCommandHandler sgSubCmdHandler = this.sgSubCmdHandlerMap.get(subLabel);
		if(sgSubCmdHandler != null) {
			UserData userData = this.prepareUserDataNew(player);
			return sgSubCmdHandler.onTabComplete(userData, player, subArgs);
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
		UserData userData = this.prepareUserDataNew(player);
		userData.setLogger(player);
		SelectionShapeNew selShape = userData.getSelectionShapeNew();
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
		new SelectionMessenger().printSelection(player, selShape, selData, selStrategy.defaultOffsetKey());
	}
	
	/**
	 * Get UserData. If there is a user data for the player, return it, otherwise create new one and return it.
	 * @param player player
	 * @return UserData which the user has
	 */
	private UserData prepareUserDataNew(Player player) {
		User user = new User(player.getUniqueID());
		UserData userData = this.userDataMap.get(user);
		if(userData == null) {
			userData = new UserData();
			SelectionShapeNew defaultShape = SelectionShapeNew.CUBOID;
			userData.setSelectionShapeNew(defaultShape);
			SelectionData selData = defaultShape.newSelectionData(player.getEntityPosition().world());
			userData.setSelectionData(selData);
			this.userDataMap.put(user, userData);
		}
		return userData;
	}
	
	/**
	 * Get UserData. If there is a user data for the player, return it, otherwise create new one and return it.
	 * @param player player
	 * @return UserData which the user has
	 */
	private UserData prepareUserData(Player player) {
		User user = new User(player.getUniqueID());
		UserData userData = this.userDataMap.get(user);
		if(userData == null) {
			userData = new UserData();
			SelectionShape defaultShape = SelectionShape.CUBOID;
			SelectionData selData = defaultShape.newSelectionData(player.getEntityPosition().world());
			userData.setSelectionShape(defaultShape);
			userData.setSelectionData(selData);
			this.userDataMap.put(user, userData);
		}
		return userData;
	}
	
}
