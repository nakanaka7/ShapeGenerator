package tokyo.nakanaka;

import tokyo.nakanaka.commandLine.CommandLine;
import tokyo.nakanaka.commandLine.CommandLineRepository;

public class CommandHandler {
	private CommandLineRepository cmdLineRepo = new CommandLineRepository();
	
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
}
