package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandLine.CommandLine;

public class RootCommandHandler {
	private CommandHandlerRepository cmdLineRepo = new CommandHandlerRepository();
	
	public void register(CommandHandler cmdHandler) {
		this.cmdLineRepo.register(cmdHandler);
	}
	
	public void onCommand(CommandLine cmdLine) {
		CommandHandler cmdHandler = this.cmdLineRepo.findBy(cmdLine.getAlias());
		Player player = cmdLine.getPlayer();
		if(cmdHandler != null) {
			boolean printUsage = cmdHandler.onCommand(player, cmdLine.getArgs());
			if(printUsage) {
				player.getLogger().print("Usage");
			}
		}
		player.getLogger().print("Invalid command");
	}
	
	public List<String> onTabComplete(CommandLine cmdLine){
		CommandHandler cmdHandler = this.cmdLineRepo.findBy(cmdLine.getAlias());
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(cmdLine.getPlayer(), cmdLine.getArgs());
		}
		return new ArrayList<>();
	}
	
	public List<String> getAliases(){
		List<String> aliasList = new ArrayList<>();
		Set<CommandHandler> set = this.cmdLineRepo.getAll();
		for(CommandHandler e : set) {
			List<String> list = e.getAliases();
			aliasList.addAll(list);
		}
		return aliasList;
	}
}
