package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

public class SgSubCommandHandlerRepository {
	private List<BranchCommandHandler> handlerList = new ArrayList<>();

	public void register(BranchCommandHandler cmdHandler) {
		this.handlerList.add(cmdHandler);
	}
	
	public BranchCommandHandler findBy(String label) {
		for(BranchCommandHandler e : this.handlerList) {
			if(e.getLabel().equals(label)) {
				return e;
			}
		}
		return null;
	}
	
	public List<BranchCommandHandler> getAll(){
		return new ArrayList<>(this.handlerList);
	}
	
	public List<String> getAliases(){
		List<String> list = new ArrayList<>();
		for(BranchCommandHandler e : this.handlerList) {
			list.add(e.getLabel());
		}
		return list;
	}

}
