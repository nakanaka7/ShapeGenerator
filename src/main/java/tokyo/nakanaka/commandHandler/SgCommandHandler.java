package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class SgCommandHandler {
	private Map<String, SubCommandHandler> subMap = new HashMap<>();
	
	public void onCommandHelp(Logger logger) {
		List<Pair<String, String>> list = new ArrayList<>();
		for(SubCommandHandler handler : this.subMap.values()) {
			CommandHelp help = handler.getCommandHelp();
			list.add(new Pair<>(help.getLabel(), help.getDescription()));
		}
	}
	
	public void onCommand(Player player, String[] args) {
		if(args.length == 0) {
			this.onCommandHelp(player.getLogger());
			return;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		if(label.equals("help")) {
			return;
		}
		
		
		SubCommandHandler cmdHandler = this.subMap.get(label);
		if(cmdHandler == null) {
			//help
			return;
		}
		boolean success = cmdHandler.onCommand(player, shiftArgs);
		if(!success) {
			CommandHelp help = cmdHandler.getCommandHelp();
			for(String line : help.getHelp()) {
				player.getLogger().print(line);
			}
		}
	}
	
	public List<String> onTabComplete(Player player, String[] args){
		if(args.length == 1) {
			return new ArrayList<>(this.subMap.keySet());
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		SubCommandHandler cmdHandler = this.subMap.get(label);
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(player, shiftArgs);
		}else {
			return new ArrayList<>();
		}
	}
}
