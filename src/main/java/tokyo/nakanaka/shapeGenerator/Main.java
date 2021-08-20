package tokyo.nakanaka.shapeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.commandSender.BlockCommandSender;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.commandSender.ConsoleCommandSender;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.event.ClickBlockEventListener;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.user.User;
import tokyo.nakanaka.shapeGenerator.user.UserRepository;

public class Main {
	private UserRepository userRepo;
	private SelectionStrategySource selStrtgSource;
	private SgCommandHandler sgCmdHandler;
	private ClickBlockEventListener evtListner;
	
	public Main(BlockCommandArgument blockArg, SelectionStrategySource selStrtgSource) {
		this.userRepo = new UserRepository();
		this.selStrtgSource = selStrtgSource;
		this.sgCmdHandler = new SgCommandHandler(blockArg, selStrtgSource);
		this.evtListner = new ClickBlockEventListener(this.userRepo, selStrtgSource);
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
			return;
		}
		user.setLogger(cmdSender);
		this.sgCmdHandler.onCommand(user, args);
	}
	
	public List<String> onSgTabComplete(CommandSender cmdSender, String[] args) {
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