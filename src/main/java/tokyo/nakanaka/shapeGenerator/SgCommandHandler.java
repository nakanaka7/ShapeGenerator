package tokyo.nakanaka.shapeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.CommandHandler;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.SemVer;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerDataRepository;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.*;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgBranchHelpConstants;

@PrivateAPI
class SgCommandHandler implements CommandHandler {
	private PlayerDataRepository playerDataRepository;
	private Map<String, CommandExecutor> cmdExecutorMap = new HashMap<>();
	private Map<String, TabCompleter> tabCompleterMap = new HashMap<>();

	SgCommandHandler(SelectionShapeStrategyRepository shapeStrtgRepo, BlockIDListFactory blockIDListFactory, PlayerDataRepository playerDataRepository) {
		this.playerDataRepository = playerDataRepository;
		this.registerCommand(new HelpCommandHandler());
		this.registerCommand(new VersionCommandHandler(new SemVer(1, 2, 0)));
		this.registerCommand(new WandCommandHandler());
		this.registerCommand(new ShapeCommandHandler(shapeStrtgRepo));
		this.registerCommand(new SelCommandHandler(shapeStrtgRepo));
		this.registerCommand(new GenrCommandHandler(shapeStrtgRepo, blockIDListFactory));
		this.registerCommand(new PhyCommandHandler());
		this.registerCommand(new ShiftCommandHandler());
		this.registerCommand(new ScaleCommandHandler());
		this.registerCommand(new MirrorCommandHandler());
		this.registerCommand(new RotCommandHandler());
		this.registerCommand(new MaxCommandHandler());
		this.registerCommand(new MinCommandHandler());
		this.registerCommand(new DelCommandHandler());
		this.registerCommand(new UndoCommandHandler());
		this.registerCommand(new RedoCommandHandler());
	}

	public void registerCommand(SubCommandHandler cmdHandler) {
		this.cmdExecutorMap.put(cmdHandler.label(), cmdHandler::onCommand);
		this.tabCompleterMap.put(cmdHandler.label(), cmdHandler::onTabComplete);
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
			cmdSender.print(cmdLogColor.error() + "Run \"" + SgBranchHelpConstants.HELP.syntax() + "\" for help");
			return;
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		CommandExecutor sgSubCmdExecutor = this.cmdExecutorMap.get(subLabel);
		if(sgSubCmdExecutor == null) {
			cmdSender.print(cmdLogColor.error() + "Unknown subcommand");
			cmdSender.print(cmdLogColor.error() + "Run \"" + SgBranchHelpConstants.HELP.syntax() + "\" for help");
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
		TabCompleter tabCompleter = this.tabCompleterMap.get(subLabel);
		if(tabCompleter != null) {
			PlayerData playerData = this.playerDataRepository.preparePlayerData(player);
			return tabCompleter.onTabComplete(playerData, player, subArgs);
		}
		return List.of();
	}

}
