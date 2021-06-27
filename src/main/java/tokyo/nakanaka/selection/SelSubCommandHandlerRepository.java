package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelSubCommandHandlerRepository {
	private Map<String, SelSubCommandHandler> map = new HashMap<>();
	
	public void register(SelSubCommandHandler handler) {
		this.map.put(handler.getCommandHelp().getLabel(), handler);
	}
	
	public List<String> getLabels(){
		return new ArrayList<>(this.map.keySet());
	}
	
	public SelSubCommandHandler findBy(String label) {
		return this.map.get(label);
	}
	
}
