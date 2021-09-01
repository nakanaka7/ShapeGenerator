package tokyo.nakanaka.shapeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Item;
import tokyo.nakanaka.NamespacedID;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.logger.LogColor;
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
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.SelCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.SgSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.ShapeCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.ShiftCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.UndoCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.WandCommandHandler;
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
		this.sgSubCmdHandlerMap.put("help", new HelpCommandHandler());
		this.sgSubCmdHandlerMap.put("wand", new WandCommandHandler());
		this.sgSubCmdHandlerMap.put("shape", new ShapeCommandHandler());
		this.sgSubCmdHandlerMap.put("sel", new SelCommandHandler());
		this.sgSubCmdHandlerMap.put("genr", new GenrCommandHandler(blockIDListFactory));
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
		Item item = evt.getItemStack().getItem();
		if(!item.equals(new Item(new NamespacedID("minecraft", "blaze_rod")))) {
			return;
		}
		evt.cancel();
		Player player = evt.getPlayer();
		UserData userData = this.prepareUserData(player);
		SelectionShape selShape = userData.getSelectionShape();
		BlockPosition blockPos = evt.getBlockPos();
		switch(evt.getHandType()) {
			case LEFT_HAND -> {
				selShape.onLeftClickBlock(userData, player, blockPos);
			}
			case RIGHT_HAND -> {
				selShape.onRightClickBlock(userData, player, blockPos);
			}
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
