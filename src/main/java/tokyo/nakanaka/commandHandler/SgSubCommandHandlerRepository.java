package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

public class SgSubCommandHandlerRepository {
	private List<SgSubCommandHandler> handlerList = new ArrayList<>();

	public void register(SgSubCommandHandler cmdLine) {
		this.handlerList.add(cmdLine);
	}
	
	public SgSubCommandHandler findBy(String label) {
		for(SgSubCommandHandler e : this.handlerList) {
			if(e.getCommandHelp().getLabel().equals(label)) {
				return e;
			}
		}
		return null;
	}
	
	public List<SgSubCommandHandler> getAll(){
		return new ArrayList<>(this.handlerList);
	}
	
	public List<String> getAliases(){
		List<String> list = new ArrayList<>();
		for(SgSubCommandHandler e : this.handlerList) {
			list.add(e.getCommandHelp().getLabel());
		}
		return list;
	}

}
