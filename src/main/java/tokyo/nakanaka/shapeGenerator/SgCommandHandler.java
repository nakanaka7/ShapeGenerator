package tokyo.nakanaka.shapeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.CommandHandler;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.DelCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.GenrCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.HelpCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MaxxCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MaxyCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MaxzCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MinxCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MinyCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MinzCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.MirrorCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.PhyCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.RedoCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.RotCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.ScaleCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.SelCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.ShapeCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.ShiftCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.UndoCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.WandCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.HelpHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;
import tokyo.nakanaka.shapeGenerator.user.UserDataRepository;

public class SgCommandHandler implements CommandHandler {
	private UserDataRepository userDataRepository;
	private Map<String, SubCommandHandler> sgSubCmdHandlerMap = new HashMap<>();
	
	{
		this.sgSubCmdHandlerMap.put("help", new HelpCommandHandler());
		this.sgSubCmdHandlerMap.put("wand", new WandCommandHandler());
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
	
	public SgCommandHandler(UserDataRepository userDataRepository, SelectionHandler selHandler, BlockIDListFactory blockIDListFactory) {
		this.userDataRepository = userDataRepository;
		this.sgSubCmdHandlerMap.put("shape", new ShapeCommandHandler(selHandler));
		this.sgSubCmdHandlerMap.put("sel", new SelCommandHandler(selHandler));
		this.sgSubCmdHandlerMap.put("genr", new GenrCommandHandler(blockIDListFactory));
	}
	
	/**
	 * Handles "/sg" command
	 * @param cmdSender a command sender
	 * @param args arguments of the command line
	 */
	@Override
	public void onCommand(CommandSender cmdSender, String[] args) {
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
		SubCommandHandler sgSubCmdHandler = this.sgSubCmdHandlerMap.get(subLabel);
		if(sgSubCmdHandler == null) {
			cmdSender.print(LogColor.RED + "Unknown subcommand");
			cmdSender.print(LogColor.RED + "Run \"" + new HelpHelp().getUsage() + "\" for help");
			return;
		}
		UserData userData = this.userDataRepository.prepareUserData(player);
		sgSubCmdHandler.onCommand(userData, player, subArgs);
	}

	/**
	 * Get a list for tab complete of "/sg" command
	 * @param cmdSender a command sender
	 * @param args arguments of the command line
	 * @return a list for tab complete of "/sg" command
	 */
	@Override
	public List<String> onTabComplete(CommandSender cmdSender, String[] args) {
		if(!(cmdSender instanceof Player player)) {
			return List.of();
		}
		if(args.length == 1) {
			return 	new ArrayList<>(this.sgSubCmdHandlerMap.keySet());
		}	
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		SubCommandHandler sgSubCmdHandler = this.sgSubCmdHandlerMap.get(subLabel);
		if(sgSubCmdHandler != null) {
			UserData userData = this.userDataRepository.prepareUserData(player);
			return sgSubCmdHandler.onTabComplete(userData, player, subArgs);
		}
		return List.of();
	}

}
