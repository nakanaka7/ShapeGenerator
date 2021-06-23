package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static tokyo.nakanaka.logger.LogConstant.*;

import tokyo.nakanaka.commandLine.CommandLine;
import tokyo.nakanaka.player.Player;

public class RootCommandHandler {
	private CommandHandlerRepository cmdHandlerRepo;
	
	public RootCommandHandler(CommandHandlerRepository cmdHandlerRepo) {
		this.cmdHandlerRepo = cmdHandlerRepo;
	}
	
	public Set<CommandHandler> getAllRegisterdCommandHandler(){
		return this.cmdHandlerRepo.getAll();
	}
	
	public void onCommand(CommandLine cmdLine) {
		Player player = cmdLine.getPlayer();
		String alias = cmdLine.getAlias();
		CommandHandler cmdHandler = this.cmdHandlerRepo.findBy(alias);
		if(cmdHandler != null) {
			boolean success = cmdHandler.onCommand(player, cmdLine.getArgs());
			if(!success) {
				player.getLogger().print(HEAD_ERROR + "Usage: " + cmdHandler.getUsage());
			}
		}else {
			player.getLogger().print(HEAD_ERROR + "Unknown command");
		}
	}
	
	public List<String> onTabComplete(CommandLine cmdLine){
		String alias = cmdLine.getAlias();
		String[] args = cmdLine.getArgs();
		if(args.length == 0) {
			return new ArrayList<>(this.cmdHandlerRepo.getAliases());
		}
		CommandHandler cmdHandler = this.cmdHandlerRepo.findBy(alias);
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(cmdLine.getPlayer(), cmdLine.getArgs());
		}else {
			return new ArrayList<>();
		}
	}
	
}
