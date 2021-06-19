package tokyo.nakanaka.commandHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandHandlerRepository {
	private Map<String, CommandHandler> cmdMap = new HashMap<>();
	private Map<String, String> aliasMap = new HashMap<>();

	public void register(CommandHandler cmdLine) {
		String label = cmdLine.getLabel();
		this.cmdMap.put(label, cmdLine);
		for(String alias : cmdLine.getAliases()) {
			this.aliasMap.put(alias, label);
		}
	}
	
	public CommandHandler findBy(String alias) {
		String label = this.aliasMap.get(alias);
		if(label == null) {
			return null;
		}
		return this.cmdMap.get(label);
	}
	
	public Set<CommandHandler> getAll(){
		return new HashSet<>(this.cmdMap.values());
	}
	
	public Set<String> getAliases(){
		return this.aliasMap.keySet();
	}

}
