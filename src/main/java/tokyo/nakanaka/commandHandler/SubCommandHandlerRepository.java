package tokyo.nakanaka.commandHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tokyo.nakanaka.commadHelp.CommandHelp;

public class SubCommandHandlerRepository {
	private Map<String, SgSubCommandHandler> cmdMap = new HashMap<>();

	public void register(SgSubCommandHandler cmdLine) {
		CommandHelp help = cmdLine.getCommandHelp();
		String label = help.getLabel();
		this.cmdMap.put(label, cmdLine);
	}
	
	public SgSubCommandHandler findBy(String label) {
		return this.cmdMap.get(label);
	}
	
	public Set<SgSubCommandHandler> getAll(){
		return new HashSet<>(this.cmdMap.values());
	}
	
	public Set<String> getAliases(){
		return this.cmdMap.keySet();
	}

}
