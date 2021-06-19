package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static tokyo.nakanaka.logger.LogConstant.*;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandLine.CommandLine;

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
		CommandHandler cmdHandler = this.cmdLineRepo.findBy(cmdLine.getAlias());
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(cmdLine.getPlayer(), cmdLine.getArgs());
		}else {
			return new ArrayList<>();
		}
	}
	
	@Deprecated
	public List<String> getAliases(){
		List<String> aliasList = new ArrayList<>();
		Set<CommandHandler> handlerSet = this.cmdLineRepo.getAll();
		for(CommandHandler handler : handlerSet) {
			aliasList.addAll(handler.getAliases());
		}
		return aliasList;
	}
}
