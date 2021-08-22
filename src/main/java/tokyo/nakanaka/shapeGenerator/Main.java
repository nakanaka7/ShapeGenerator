package tokyo.nakanaka.shapeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import tokyo.nakanaka.CommandHandler;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.commandSender.BlockCommandSender;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.commandSender.ConsoleCommandSender;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.event.ClickBlockEventListener;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.commandHandler.HelpCommandHandler;
import tokyo.nakanaka.shapeGenerator.commandHandler.WandCommandHandler;
import tokyo.nakanaka.shapeGenerator.commandHelp.HelpHelp;
import tokyo.nakanaka.shapeGenerator.user.User;
import tokyo.nakanaka.shapeGenerator.user.UserRepository;

public class Main {
	private UserRepository userRepo;
	private SelectionStrategySource selStrtgSource;
	private SgCommandHandler sgCmdHandler;
	private ClickBlockEventListener evtListner;
	private Map<String, CommandHandler> cmdHandlerMap = new HashMap<>();
	
	public Main(BlockCommandArgument blockArg, SelectionStrategySource selStrtgSource) {
		this.userRepo = new UserRepository();
		this.selStrtgSource = selStrtgSource;
		this.sgCmdHandler = new SgCommandHandler(blockArg, selStrtgSource);
		this.evtListner = new ClickBlockEventListener(this.userRepo, selStrtgSource);
		this.cmdHandlerMap.put("help", new HelpCommandHandler());
		this.cmdHandlerMap.put("wand", new WandCommandHandler());
	}
	
	public void onSgCommandNew(CommandSender cmdSender, String[] args) {
		User user;
		if(cmdSender instanceof Player playerCmdSender) {
			UUID uid = playerCmdSender.getUniqueID();
			user = this.userRepo.getHumanUser(uid);
			if(user == null) {
				user = new User(uid);
				this.userRepo.registerHumanUser(user);
				MainFunctions.setDefaultSelection(this.selStrtgSource, user);
			}
			user.setBlockPosition(playerCmdSender.getBlockPosition());
		}else if(cmdSender instanceof BlockCommandSender blockCmdSender) {
			if(args.length == 0 || !args[0].startsWith("@")) {
				blockCmdSender.print(LogDesignColor.ERROR + "Type \"@[name]\" after \"/sg\" to specify a dummy player");
				return;
			}
			BlockCmdLine blockCmdLine = toBlockCmdLine(args);
			String name = blockCmdLine.name();
			user = this.userRepo.getBlockUser(name);
			if(user == null) {
				user = new User(UUID.randomUUID(), name);
				this.userRepo.registerBlockPlayer(user);
				MainFunctions.setDefaultSelection(this.selStrtgSource, user);
			}
			user.setBlockPosition(blockCmdSender.getBlockPosition());
			args = blockCmdLine.shiftArgs();
		}else {
			user = this.userRepo.getConsoleUser();
		}
		user.setLogger(cmdSender);
		this.sgCmdHandler.onCommand(user, args);
	}
	
	public void onSgCommand(CommandSender cmdSender, String[] args) {
		if(args.length == 0) {
			cmdSender.print(LogColor.RED + "Usage: /sg <subcommand>");
			cmdSender.print(LogColor.RED + "Run \"" + new HelpHelp().getUsage() + "\" for help");
			return;
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		if(this.cmdHandlerMap.containsKey(subLabel)) {
			this.cmdHandlerMap.get(subLabel).onCommand(cmdSender, subArgs);
			return;
		}
		
		User user;
		if(cmdSender instanceof Player player) {
			UUID uid = player.getUniqueID();
			user = this.userRepo.getHumanUser(uid);
			if(user == null) {
				user = new User(uid);
				this.userRepo.registerHumanUser(user);
				MainFunctions.setDefaultSelection(this.selStrtgSource, user);
			}
			user.setBlockPosition(player.getBlockPosition());
		}else {
			return;
		}
		user.setLogger(cmdSender);
		this.sgCmdHandler.onCommand(user, args);
	}
	
	public List<String> onSgTabComplete(CommandSender cmdSender, String[] args) {
		if(args.length == 0) {
			return List.of();
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		if(this.cmdHandlerMap.containsKey(subLabel)) {
			return this.cmdHandlerMap.get(subLabel).onTabComplete(cmdSender, subArgs);
		}
		User user; 
		if(cmdSender instanceof Player playerCmdSender) {
			UUID uid = playerCmdSender.getUniqueID();
			user = this.userRepo.getHumanUser(uid);
			if(user == null) {
				user = new User(uid);
				this.userRepo.registerHumanUser(user);
				MainFunctions.setDefaultSelection(this.selStrtgSource, user);
			}
			user.setBlockPosition(playerCmdSender.getBlockPosition());
		}else {
			return new ArrayList<>();
		}
		return this.sgCmdHandler.onTabComplete(user, args);
	}
	
	public List<String> onSgTabCompleteOld(CommandSender cmdSender, String[] args) {
		User user; 
		if(cmdSender instanceof Player playerCmdSender) {
			if(!args[0].startsWith("@")) {
				UUID uid = playerCmdSender.getUniqueID();
				user = this.userRepo.getHumanUser(uid);
				if(user == null) {
					user = new User(uid);
					this.userRepo.registerHumanUser(user);
					MainFunctions.setDefaultSelection(this.selStrtgSource, user);
				}
				user.setBlockPosition(playerCmdSender.getBlockPosition());
			}else {
				BlockCmdLine blockCmdLine = toBlockCmdLine(args);
				String name = blockCmdLine.name();
				user = this.userRepo.getBlockUser(name);
				if(user == null) {
					user = new User(UUID.randomUUID(), name);
					MainFunctions.setDefaultSelection(this.selStrtgSource, user);
				}
				user.setBlockPosition(playerCmdSender.getBlockPosition());
				args = blockCmdLine.shiftArgs();
			}
		}else if(cmdSender instanceof ConsoleCommandSender) {
			user = this.userRepo.getConsoleUser();
		}else {
			//unreachable
			return null;
		}
		return this.sgCmdHandler.onTabComplete(user, args);
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
