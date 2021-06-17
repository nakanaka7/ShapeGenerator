package tokyo.nakanaka;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tokyo.nakanaka.commandLine.CommandLine;
import tokyo.nakanaka.commandLine.CommandLineRepository;

public class CommandHandler {
	private CommandLineRepository cmdLineRepo = new CommandLineRepository();
	
	public void register(CommandLine cmdLine) {
		this.cmdLineRepo.register(cmdLine);
	}
	
	public void onCommand(Player player, String alias, String[] args) {
		CommandLine cmdLine = this.cmdLineRepo.findBy(alias);
		if(cmdLine != null) {
			boolean printUsage = cmdLine.onCommand(player, args);
			if(printUsage) {
				player.getLogger().print("Usage");
			}
		}
		player.getLogger().print("Invalid command");
	}
	
	public List<String> onTabComplete(Player player, String alias, String[] args){
		CommandLine cmdLine = this.cmdLineRepo.findBy(alias);
		if(cmdLine != null) {
			return cmdLine.onTabComplete(player, args);
		}
		return new ArrayList<>();
	}
	
	public List<String> getAliases(){
		List<String> aliasList = new ArrayList<>();
		Set<CommandLine> set = this.cmdLineRepo.getAll();
		for(CommandLine e : set) {
			List<String> list = e.getAliases();
			aliasList.addAll(list);
		}
		return aliasList;
	}
}
