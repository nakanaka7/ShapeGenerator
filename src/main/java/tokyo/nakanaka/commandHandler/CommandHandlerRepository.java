package tokyo.nakanaka.commandHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tokyo.nakanaka.commadHelp.CommandHelp;

public class CommandHandlerRepository {
	private Map<String, SubCommandHandler> cmdMap = new HashMap<>();

	public void register(SubCommandHandler cmdLine) {
		CommandHelp help = cmdLine.getCommandHelp();
		String label = help.getLabel();
		this.cmdMap.put(label, cmdLine);
	}
	
	public SubCommandHandler findBy(String label) {
		return this.cmdMap.get(label);
	}
	
	public Set<SubCommandHandler> getAll(){
		return new HashSet<>(this.cmdMap.values());
	}
	
	public Set<String> getAliases(){
		return this.cmdMap.keySet();
	}

}
