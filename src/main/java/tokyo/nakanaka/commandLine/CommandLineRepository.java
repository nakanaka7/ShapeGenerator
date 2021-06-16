package tokyo.nakanaka.commandLine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandLineRepository {
	private Map<String, CommandLine> cmdMap = new HashMap<>();
	private Map<String, String> aliasMap = new HashMap<>();

	public void register(CommandLine cmdLine) {
		String label = cmdLine.getLabel();
		this.cmdMap.put(label, cmdLine);
		for(String alias : cmdLine.getAliases()) {
			this.aliasMap.put(alias, label);
		}
	}
	
	public CommandLine findBy(String alias) {
		String label = this.aliasMap.get(alias);
		if(label == null) {
			return null;
		}
		return this.cmdMap.get(label);
	}
	
	public Set<CommandLine> getAll(){
		return new HashSet<>(this.cmdMap.values());
	}

}
