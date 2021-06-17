package tokyo.nakanaka;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tokyo.nakanaka.commandHandler.CommandHandler;
import tokyo.nakanaka.commandHandler.CommandHandlerRepository;

public class RootCommandHandler {
	private CommandHandlerRepository cmdLineRepo = new CommandHandlerRepository();
	
	public void register(CommandHandler cmdHandler) {
		this.cmdLineRepo.register(cmdHandler);
	}
	
	public void onCommand(Player player, String alias, String[] args) {
		CommandHandler cmdHandler = this.cmdLineRepo.findBy(alias);
		if(cmdHandler != null) {
			boolean printUsage = cmdHandler.onCommand(player, args);
			if(printUsage) {
				player.getLogger().print("Usage");
			}
		}
		player.getLogger().print("Invalid command");
	}
	
	public List<String> onTabComplete(Player player, String alias, String[] args){
		CommandHandler cmdHandler = this.cmdLineRepo.findBy(alias);
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(player, args);
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
