package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionShape;

public class SelSubCommandHandlerUtils {
	private SelSubCommandHandlerUtils() {	
	}
	
	public static List<String> selectionMessage(SelectionShape selShape, SelectionData selData) {
		List<String> lines = new ArrayList<>();
		lines.add("--- [" + LogColor.GOLD + selShape + " Selection" + LogColor.RESET + "] ---------------------");
		for(Entry<String, String> e : selData.getRegionData().toLinkedHashMap().entrySet()) {
			lines.add(LogColor.GOLD + e.getKey() + ": " + e.getValue());
		}
		return lines;
	}
	
}
