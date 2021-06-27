package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.player.Player;

public class RootCommandHandlerNew implements CommandHandler{
	private String label;
	private Map<String, SubCommandHandler> cmdMap = new HashMap<>();

	public void register(SubCommandHandler cmdLine) {
		CommandHelp help = cmdLine.getCommandHelp();
		String label = help.getLabel();
		this.cmdMap.put(label, cmdLine);
	}
	
	public RootCommandHandlerNew(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public List<Pair<String, String>> getSubCommmandDescriptions(){
		Set<SubCommandHandler> set = new HashSet<>(this.cmdMap.values());
		List<Pair<String, String>> list = new ArrayList<>();
		for(SubCommandHandler handler : set) {
			CommandHelp help = handler.getCommandHelp();
			list.add(new Pair<>(help.getLabel(), help.getDescription()));
		}
		return list;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 0) {
			return false;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		SubCommandHandler cmdHandler = this.cmdMap.get(label);
		if(cmdHandler == null) {
			return false;
		}
		boolean success = cmdHandler.onCommand(player, shiftArgs);
		if(!success) {
			CommandHelp help = cmdHandler.getCommandHelp();
			for(String line : help.getHelp()) {
				player.getLogger().print(line);
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(Player player, String[] args){
		if(args.length == 1) {
			return new ArrayList<>(this.cmdMap.keySet());
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		SubCommandHandler cmdHandler = this.cmdMap.get(label);
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(player, shiftArgs);
		}else {
			return new ArrayList<>();
		}
	}
	
}
