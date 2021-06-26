package tokyo.nakanaka.commandHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tokyo.nakanaka.commadHelp.CommandHelp;

public class CommandHandlerRepository {
	private Map<String, CommandHandler> cmdMap = new HashMap<>();

	public void register(CommandHandler cmdLine) {
		CommandHelp help = cmdLine.getCommandHelp();
		String label = help.getLabel();
		this.cmdMap.put(label, cmdLine);
	}
	
	public CommandHandler findBy(String label) {
		return this.cmdMap.get(label);
	}
	
	public Set<CommandHandler> getAll(){
		return new HashSet<>(this.cmdMap.values());
	}
	
	public Set<String> getAliases(){
		return this.cmdMap.keySet();
	}

}
