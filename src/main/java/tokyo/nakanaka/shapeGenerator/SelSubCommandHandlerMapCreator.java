package tokyo.nakanaka.shapeGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

public class SelSubCommandHandlerMapCreator {
	private Map<SelectionShape, Map<String, SubCommandHandler>> handlerMap = new HashMap<>();
	

	public SelSubCommandHandlerMapCreator(Map<SelectionShape, SelectionShapeStrategy> selStrtgMap) {
		for(Entry<SelectionShape, SelectionShapeStrategy> e : selStrtgMap.entrySet()) {
			
		}
	}
	
	public Map<String, SubCommandHandler> selSubCommandHandlerMap(SelectionShape selShape){
		return this.handlerMap.get(selShape);
	}
	
}
