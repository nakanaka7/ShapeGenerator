package tokyo.nakanaka.shapeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.*;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.*;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.HelpHelp;
import tokyo.nakanaka.shapeGenerator.user.User;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Main class for the project. 
 */
public class Main {
	private Map<String, SgSubCommandHandler> sgSubCmdHandlerMap = new HashMap<>();
	private Map<User, UserData> userDataMap = new HashMap<>();
	
	public Main(BlockIDListFactory blockIDListFactory) {
		Map<SelectionShape, SelectionShapeStrategy> selStrtgMap = new HashMap<>();
		selStrtgMap.put(SelectionShape.CUBOID, new CuboidSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.DIAMOND, new DiamondSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.SPHERE, new SphereSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.TORUS, new TorusSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.LINE, new LineSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.TRIANGLE, new TriangleSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.TETRAHEDRON, new TetrahedronSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.REGULAR_POLYGON, new RegularPolygonSelectionShapeStrategy());
		SelectionHandler selHandler = new SelectionHandler(selStrtgMap);
		this.sgSubCmdHandlerMap.put("help", new HelpCommandHandler());
		this.sgSubCmdHandlerMap.put("wand", new WandCommandHandler());
		this.sgSubCmdHandlerMap.put("shape", new ShapeCommandHandler(selHandler));
		this.sgSubCmdHandlerMap.put("sel", new SelCommandHandler(selHandler));
		this.sgSubCmdHandlerMap.put("genr", new GenrCommandHandler(selHandler, blockIDListFactory));
		this.sgSubCmdHandlerMap.put("phy", new PhyCommandHandler());
		this.sgSubCmdHandlerMap.put("shift", new ShiftCommandHandler());
		this.sgSubCmdHandlerMap.put("scale", new ScaleCommandHandler());
		this.sgSubCmdHandlerMap.put("mirror", new MirrorCommandHandler());
		this.sgSubCmdHandlerMap.put("rot", new RotCommandHandler());
		this.sgSubCmdHandlerMap.put("maxx", new MaxxCommandHandler());
		this.sgSubCmdHandlerMap.put("maxy", new MaxyCommandHandler());
		this.sgSubCmdHandlerMap.put("maxz", new MaxzCommandHandler());
		this.sgSubCmdHandlerMap.put("minx", new MinxCommandHandler());
		this.sgSubCmdHandlerMap.put("miny", new MinyCommandHandler());
		this.sgSubCmdHandlerMap.put("minz", new MinzCommandHandler());
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
		UserData userData = this.prepareUserData(player);
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
			UserData userData = this.prepareUserData(player);
			return sgSubCmdHandler.onTabComplete(userData, player, subArgs);
		}
		return List.of();
	}
	/**
	 * Handles a click block event
	 * @param evt a click block event
	 */
	public void onClickBlockEvent(ClickBlockEvent evt) {
		//cancel the event if the clicked item is not "minecraft:blaze_rod"
		Item item = evt.getItemStack().getItem();
		if(!item.equals(new Item(new NamespacedID("minecraft", "blaze_rod")))) {
			return;
		}
		evt.cancel();
		Player player = evt.getPlayer();
		BlockPosition blockPos = evt.getBlockPos();
		UserData userData = this.prepareUserData(player);
		SelectionShape selShape = userData.getSelectionShape();
		//reset the selection data if the clicked block world is not equal to the world of the selection data
		World evtWorld = blockPos.world();
		if(!evtWorld.equals(userData.getSelectionData().getWorld())) {
			SelectionData newSelData = selShape.newSelectionData(evtWorld);
			userData.setSelectionData(newSelData);
		}
		//update the selection data
		LinkedHashMap<String,Object> regDataMap = userData.getSelectionData().getRegionDataMap();
		BlockVector3D v = new BlockVector3D(blockPos.x(), blockPos.y(), blockPos.z());
		switch(evt.getHandType()) {
			case LEFT_HAND -> {
			}
			case RIGHT_HAND -> {
			}
		}
		//print the selection message
		List<String> lines = Utils.getSelectionMessageLines(userData.getSelectionData());
		for(String line : lines) {
			player.print(line);
		}
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
