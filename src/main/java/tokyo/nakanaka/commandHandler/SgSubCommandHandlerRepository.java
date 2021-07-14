package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

public class SgSubCommandHandlerRepository {
	private List<CommandHandler> handlerList = new ArrayList<>();

	public void register(CommandHandler cmdHandler) {
		this.handlerList.add(cmdHandler);
	}
	
	public CommandHandler findBy(String label) {
		for(CommandHandler e : this.handlerList) {
			if(e.getLabel().equals(label)) {
				return e;
			}
		}
		return null;
	}
	
	public List<CommandHandler> getAll(){
		return new ArrayList<>(this.handlerList);
	}
	
	public List<String> getAliases(){
		List<String> list = new ArrayList<>();
		for(CommandHandler e : this.handlerList) {
			list.add(e.getLabel());
		}
		return list;
	}

}
