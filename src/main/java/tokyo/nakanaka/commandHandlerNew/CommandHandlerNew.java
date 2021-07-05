package tokyo.nakanaka.commandHandlerNew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.player.Player;

public class CommandHandlerNew {
	private CommandHandleStrategy cmdHandleStretegy;
	private Map<String, CommandHandlerNew> subMap = new HashMap<>();
	
	public CommandHandlerNew(CommandHandleStrategy cmdHandleStretegy) {
		this.cmdHandleStretegy = cmdHandleStretegy;
	}

	public void registerSub(CommandHandlerNew cmdHandler) {
		this.subMap.put(cmdHandler.getLabel(), cmdHandler);
	}
	
	public List<String> getRegisteredSubLabels(){
		return new ArrayList<>(this.subMap.keySet());
	}
	
	public void unregisterSub(String label) {
		this.subMap.remove(label);
	}

	public String getLabel() {
		return this.cmdHandleStretegy.getLabel();
	}
	
	public void onCommand(Player player, String[] args) {
		if(args.length != 0 && this.subMap.containsKey(args[0])) {
			String subLabel = args[0];
			String[] subArgs = new String[args.length - 1];
			System.arraycopy(args, 1, subArgs, 0, args.length - 1);
			this.subMap.get(subLabel)
					.onCommand(player, subArgs);
		}
		//boolean success = this.cmdHandleStretegy.onCommand(player, args);
	}
	
	public List<String> onTabComplete(String[] args){
		if(args.length != 0 && this.subMap.containsKey(args[0])) {
			String subLabel = args[0];
			String[] subArgs = new String[args.length - 1];
			System.arraycopy(args, 1, subArgs, 0, args.length - 1);
			return this.subMap.get(subLabel).onTabComplete(subArgs);
		}else {
			return this.cmdHandleStretegy.onTabComplete(args);
		}
	}
	
}
