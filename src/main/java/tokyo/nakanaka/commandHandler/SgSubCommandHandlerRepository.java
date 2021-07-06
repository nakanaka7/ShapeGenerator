package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.commadHelp.CommandHelp;

public class SgSubCommandHandlerRepository {
	private Map<String, SgSubCommandHandler> cmdMap = new HashMap<>();
	private List<String> labelList = new ArrayList<>();

	public void register(SgSubCommandHandler cmdLine) {
		CommandHelp help = cmdLine.getCommandHelp();
		String label = help.getLabel();
		this.cmdMap.put(label, cmdLine);
		this.labelList.add(label);
	}
	
	public SgSubCommandHandler findBy(String label) {
		return this.cmdMap.get(label);
	}
	
	public List<SgSubCommandHandler> getAll(){
		return new ArrayList<>(this.cmdMap.values());
	}
	
	public List<String> getAliases(){
		return new ArrayList<>(this.cmdMap.keySet());
	}

}
