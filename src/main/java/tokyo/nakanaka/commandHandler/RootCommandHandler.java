package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static tokyo.nakanaka.logger.LogConstant.*;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandLine.CommandLine;

public class RootCommandHandler {
	private CommandHandlerRepository cmdLineRepo = new CommandHandlerRepository();
	
	public void register(CommandHandler cmdHandler) {
		this.cmdLineRepo.register(cmdHandler);
	}
	
	public void onCommand(CommandLine cmdLine) {
		Player player = cmdLine.getPlayer();
		String alias = cmdLine.getAlias();
		if(alias.equals("help")) {
			player.getLogger().print(HEAD_NORMAL + "Command Help");
			Set<CommandHandler> cmdHandlerSet = this.cmdLineRepo.getAll();
			for(CommandHandler cmdHandler : cmdHandlerSet) {
				List<String> cmdAliasList = cmdHandler.getAliases();
				player.getLogger().print(INDENT_NORMAL + String.join("/ ", cmdAliasList) + ": " + cmdHandler.getDescription());
			}
			return;
		}
		CommandHandler cmdHandler = this.cmdLineRepo.findBy(alias);
		if(cmdHandler != null) {
			boolean success = cmdHandler.onCommand(player, cmdLine.getArgs());
			if(!success) {
				player.getLogger().print("Usage: " + cmdHandler.getUsage());
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
	
	public List<String> getAliases(){
		List<String> aliasList = new ArrayList<>();
		aliasList.add("help");
		Set<CommandHandler> handlerSet = this.cmdLineRepo.getAll();
		for(CommandHandler handler : handlerSet) {
			aliasList.addAll(handler.getAliases());
		}
		return aliasList;
	}
}
