package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static tokyo.nakanaka.logger.LogConstant.*;

import tokyo.nakanaka.commandLine.CommandLine;
import tokyo.nakanaka.player.Player;

public class RootCommandHandler {
	private CommandHandlerRepository cmdLineRepo;
	
	public RootCommandHandler(CommandHandlerRepository cmdLineRepo) {
		this.cmdLineRepo = cmdLineRepo;
	}
	
	public Set<CommandHandler> getAllRegisterdCommandHandler(){
		return this.cmdLineRepo.getAll();
	}
	
	public void onCommand(CommandLine cmdLine) {
		Player player = cmdLine.getPlayer();
		String alias = cmdLine.getAlias();
		CommandHandler cmdHandler = this.cmdLineRepo.findBy(alias);
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
			return new ArrayList<>(this.cmdLineRepo.getAliases());
		}
		CommandHandler cmdHandler = this.cmdLineRepo.findBy(alias);
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(cmdLine.getPlayer(), cmdLine.getArgs());
		}else {
			return new ArrayList<>();
		}
	}
	
}
