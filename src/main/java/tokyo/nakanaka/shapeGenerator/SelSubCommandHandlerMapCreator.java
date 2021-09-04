package tokyo.nakanaka.shapeGenerator;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

public class SelSubCommandHandlerMapCreator {
	private Map<SelectionShape, Map<String, SelSubCommandHandler>> handlerMap = new HashMap<>();
	

	public SelSubCommandHandlerMapCreator(Map<SelectionShape, SelectionShapeStrategy> selStrtgMap) {
		
	}
	
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap(SelectionShape selShape){
		return this.handlerMap.get(selShape);
	}
	
}
