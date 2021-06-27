package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelCommandHandlerRepository {
	private Map<String, SelCommandHandler> map = new HashMap<>();
	
	public void register(SelCommandHandler handler) {
		this.map.put(handler.getCommandHelp().getLabel(), handler);
	}
	
	public List<String> getLabels(){
		return new ArrayList<>(this.map.keySet());
	}
	
	public SelCommandHandler findBy(String label) {
		return this.map.get(label);
	}
	
}
