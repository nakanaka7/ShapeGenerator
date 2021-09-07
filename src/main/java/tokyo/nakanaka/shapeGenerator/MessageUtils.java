package tokyo.nakanaka.shapeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import tokyo.nakanaka.logger.LogColor;

public class MessageUtils {
	private MessageUtils() {	
	}
	
	public static List<String> selectionMessage(SelectionShape selShape, SelectionData selData) {
		List<String> lines = new ArrayList<>();
		lines.add("--- [" + LogColor.GOLD + selShape + " Selection" + LogColor.RESET + "] ---------------------");
		for(Entry<String, String> e : selData.toLinkedHashMap().entrySet()) {
			lines.add(LogColor.GOLD + e.getKey() + ": " + LogColor.RESET + e.getValue());
		}
		return lines;
	}
	
}
