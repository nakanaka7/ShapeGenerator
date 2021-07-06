package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

public class SgSubCommandHandlerRepository {
	private List<SgSubCommandHandler> handlerList = new ArrayList<>();

	public void register(SgSubCommandHandler cmdHandler) {
		this.handlerList.add(cmdHandler);
	}
	
	public SgSubCommandHandler findBy(String label) {
		for(SgSubCommandHandler e : this.handlerList) {
			if(e.getLabel().equals(label)) {
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
			list.add(e.getLabel());
		}
		return list;
	}

}
