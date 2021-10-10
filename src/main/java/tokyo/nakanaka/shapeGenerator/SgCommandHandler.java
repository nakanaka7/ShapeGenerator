package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.CommandHandler;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.SemVer;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerDataRepository;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.*;

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
		this.registerCommand(SgSublabel.HELP, new HelpCommandHandler());
		this.registerCommand(SgSublabel.VERSION, new VersionCommandHandler(new SemVer(1, 2, 0)));
		this.registerCommand(SgSublabel.WAND, new WandCommandHandler());
		this.registerCommand(SgSublabel.SHAPE, new ShapeCommandHandler(shapeStrtgRepo));
		this.registerCommand(SgSublabel.SEL, new SelCommandHandler(shapeStrtgRepo));
		this.registerCommand(SgSublabel.GENR, new GenrCommandHandler(shapeStrtgRepo, blockIDListFactory));
		this.registerCommand(SgSublabel.PHY, new PhyCommandHandler());
		this.registerCommand(SgSublabel.SHIFT, new ShiftCommandHandler());
		this.registerCommand(SgSublabel.SCALE, new ScaleCommandHandler());
		this.registerCommand(SgSublabel.MIRROR, new MirrorCommandHandler());
		this.registerCommand(SgSublabel.ROT, new RotCommandHandler());
		this.registerCommand(SgSublabel.MAX, new MaxCommandHandler());
		this.registerCommand(SgSublabel.MIN, new MinCommandHandler());
		this.registerCommand(SgSublabel.DEL, new DelCommandHandler());
		this.registerCommand(SgSublabel.UNDO, new UndoCommandHandler());
		this.registerCommand(SgSublabel.REDO, new RedoCommandHandler());
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
