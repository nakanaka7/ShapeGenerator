package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.CommandHandler;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.SemVer;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerDataRepository;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.GenrCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.SelCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.ShapeCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.del.DelCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.del.DelTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.help.HelpCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.help.HelpTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.max.MaxCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.max.MaxTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.min.MinCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.min.MinTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.mirror.MirrorCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.mirror.MirrorTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.phy.PhyCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.phy.PhyTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.redo.RedoCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.redo.RedoTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.rot.RotCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.rot.RotTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.scale.ScaleCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.scale.ScaleTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.shift.ShiftCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.shift.ShiftTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.undo.UndoCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.undo.UndoTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.version.VersionCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.version.VersionTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.wand.WandCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.wand.WandTabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PrivateAPI
class SgCommandHandler implements CommandHandler {
	private PlayerDataRepository playerDataRepository;
	private Map<String, SgSubcommandExecutor> cmdExecutorMap = new HashMap<>();
	private Map<String, SgSubcommandTabCompleter> tabCompleterMap = new HashMap<>();

	SgCommandHandler(SelectionShapeStrategyRepository shapeStrtgRepo, BlockIDListFactory blockIDListFactory, PlayerDataRepository playerDataRepository) {
		this.playerDataRepository = playerDataRepository;
		this.cmdExecutorMap.put(SgSublabel.HELP, new HelpCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.HELP, new HelpTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.VERSION, new VersionCommandExecutor(new SemVer(1, 2, 0)));
		this.tabCompleterMap.put(SgSublabel.VERSION, new VersionTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.WAND, new WandCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.WAND, new WandTabCompleter());
		this.registerCommand(SgSublabel.SHAPE, new ShapeCommandHandler(shapeStrtgRepo));
		this.registerCommand(SgSublabel.SEL, new SelCommandHandler(shapeStrtgRepo));
		this.registerCommand(SgSublabel.GENR, new GenrCommandHandler(shapeStrtgRepo, blockIDListFactory));
		this.cmdExecutorMap.put(SgSublabel.PHY, new PhyCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.PHY, new PhyTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.SHIFT, new ShiftCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.SHIFT, new ShiftTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.SCALE, new ScaleCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.SCALE, new ScaleTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.MIRROR, new MirrorCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.MIRROR, new MirrorTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.ROT, new RotCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.ROT, new RotTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.MAX, new MaxCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.MAX, new MaxTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.MIN, new MinCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.MIN, new MinTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.DEL, new DelCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.DEL, new DelTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.UNDO, new UndoCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.UNDO, new UndoTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.REDO, new RedoCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.REDO, new RedoTabCompleter());
	}

	public void registerCommand(String label, SubCommandHandler cmdHandler) {
		this.cmdExecutorMap.put(label, cmdHandler::onCommand);
		this.tabCompleterMap.put(label, cmdHandler::onTabComplete);
	}

	/**
	 * Handles "/sg" command
	 * @param cmdSender a command sender
	 * @param args arguments of the command line
	 */
	@Override
	public void onCommand(CommandSender cmdSender, String[] args) {
		CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);
		if(!(cmdSender instanceof Player player)) {
			cmdSender.print(cmdLogColor.error() + "Player only command");
			return;
		}
		if(args.length == 0) {
			cmdSender.print(cmdLogColor.error() + "Usage: /sg <subcommand>");
			cmdSender.print(cmdLogColor.error() + "Run \"/sg help [subcommand]\" for help");
			return;
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		SgSubcommandExecutor sgSubCmdExecutor = this.cmdExecutorMap.get(subLabel);
		if(sgSubCmdExecutor == null) {
			cmdSender.print(cmdLogColor.error() + "Unknown subcommand");
			cmdSender.print(cmdLogColor.error() + "Run \"/sg help [subcommand]\" for help");
			return;
		}
		PlayerData playerData = this.playerDataRepository.preparePlayerData(player);
		sgSubCmdExecutor.onCommand(playerData, player, subArgs, cmdLogColor);
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
			return 	new ArrayList<>(this.tabCompleterMap.keySet());
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		SgSubcommandTabCompleter tabCompleter = this.tabCompleterMap.get(subLabel);
		if(tabCompleter != null) {
			PlayerData playerData = this.playerDataRepository.preparePlayerData(player);
			return tabCompleter.onTabComplete(playerData, player, subArgs);
		}
		return List.of();
	}

}
