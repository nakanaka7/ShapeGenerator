package tokyo.nakanaka;

import java.util.List;
import java.util.UUID;

import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.commandSender.BlockCommandSender;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.commandSender.ConsoleCommandSender;
import tokyo.nakanaka.commandSender.PlayerCommandSender;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.event.ClickBlockEventListener;
import tokyo.nakanaka.logger.LogDesignColor;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.player.PlayerRepository;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;

public class Main {
	private PlayerRepository playerRepo;
	private SelectionStrategySource selStrtgSource;
	private SgCommandHandler sgCmdHandler;
	private ClickBlockEventListener evtListner;
	
	public Main(BlockCommandArgument blockArg, SelectionStrategySource selStrtgSource) {
		this.playerRepo = new PlayerRepository();
		this.selStrtgSource = selStrtgSource;
		this.sgCmdHandler = new SgCommandHandler(blockArg, selStrtgSource);
		this.evtListner = new ClickBlockEventListener(this.playerRepo, selStrtgSource);
	}
	
	public void onSgCommand(CommandSender cmdSender, String[] args) {
		Player player;
		if(cmdSender instanceof PlayerCommandSender playerCmdSender) {
			UUID uid = playerCmdSender.getUniqueID();
			player = this.playerRepo.getHumanPlayer(uid);
			if(player == null) {
				player = new Player(uid);
				this.playerRepo.registerHumanPlayer(player);
				MainFunctions.setDefaultSelection(this.selStrtgSource, player);
			}
			player.setBlockPosition(playerCmdSender.getBlockPosition());
		}else if(cmdSender instanceof BlockCommandSender blockCmdSender) {
			if(args.length == 0 || !args[0].startsWith("@")) {
				blockCmdSender.print(LogDesignColor.ERROR + "Type \"@[name]\" after \"/sg\" to specify a dummy player");
				return;
			}
			BlockCmdLine blockCmdLine = toBlockCmdLine(args);
			String name = blockCmdLine.name();
			player = this.playerRepo.getBlockPlayer(name);
			if(player == null) {
				player = new Player(UUID.randomUUID(), name);
				this.playerRepo.registerBlockPlayer(player);
				MainFunctions.setDefaultSelection(this.selStrtgSource, player);
			}
			player.setBlockPosition(blockCmdSender.getBlockPosition());
			args = blockCmdLine.shiftArgs();
		}else {
			player = this.playerRepo.getConsolePlayer();
		}
		player.setLogger(cmdSender);
		this.sgCmdHandler.onCommand(player, args);
	}
	
	public List<String> onSgTabComplete(CommandSender cmdSender, String[] args) {
		Player player; 
		if(cmdSender instanceof PlayerCommandSender playerCmdSender) {
			if(!args[0].startsWith("@")) {
				UUID uid = playerCmdSender.getUniqueID();
				player = this.playerRepo.getHumanPlayer(uid);
				if(player == null) {
					player = new Player(uid);
					this.playerRepo.registerHumanPlayer(player);
					MainFunctions.setDefaultSelection(this.selStrtgSource, player);
				}
				player.setBlockPosition(playerCmdSender.getBlockPosition());
			}else {
				BlockCmdLine blockCmdLine = toBlockCmdLine(args);
				String name = blockCmdLine.name();
				player = this.playerRepo.getBlockPlayer(name);
				if(player == null) {
					player = new Player(UUID.randomUUID(), name);
					MainFunctions.setDefaultSelection(this.selStrtgSource, player);
				}
				player.setBlockPosition(playerCmdSender.getBlockPosition());
				args = blockCmdLine.shiftArgs();
			}
		}else if(cmdSender instanceof ConsoleCommandSender) {
			player = this.playerRepo.getConsolePlayer();
		}else {
			//unreachable
			return null;
		}
		return this.sgCmdHandler.onTabComplete(player, args);
	}
	
	private static record BlockCmdLine(String name, String[] shiftArgs) {};
	
	private static BlockCmdLine toBlockCmdLine(String[] args) {
		if(args.length == 0 || !args[0].startsWith("@")) {
			throw new IllegalArgumentException();
		}
		String name = args[0].substring(1);
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		return new BlockCmdLine(name, shiftArgs);
	}
	
	public void onClickBlockEvent(ClickBlockEvent evt) {
		this.evtListner.onClickBlockEvent(evt);
	}
	
}
