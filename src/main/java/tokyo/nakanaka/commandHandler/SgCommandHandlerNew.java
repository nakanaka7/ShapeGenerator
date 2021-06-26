package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.player.Player;

public class SgCommandHandlerNew {
	private Map<String, CommandHandler> cmdMap = new HashMap<>();

	public void register(CommandHandler cmdLine) {
		CommandHelp help = cmdLine.getCommandHelp();
		String label = help.getLabel();
		this.cmdMap.put(label, cmdLine);
	}
		
	public void onCommand(Player player, String alias, String[] args) {
		CommandHandler cmdHandler = this.cmdMap.get(alias);
		if(cmdHandler != null) {
			boolean success = cmdHandler.onCommand(player, args);
			if(!success) {
				CommandHelp help = cmdHandler.getCommandHelp();
				for(String line : help.getHelp()) {
					player.getLogger().print(line);
				}
			}
		}else {
			player.getLogger().print(HEAD_ERROR + "Unknown command");
		}
	}
	
	public List<String> onTabComplete(Player player, String alias, String[] args){
		if(args.length == 0) {
			return new ArrayList<>(this.cmdMap.keySet());
		}
		CommandHandler cmdHandler = this.cmdMap.get(alias);
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(player, args);
		}else {
			return new ArrayList<>();
		}
	}

}
