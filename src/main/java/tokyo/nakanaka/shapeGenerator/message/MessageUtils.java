package tokyo.nakanaka.shapeGenerator.message;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionShape;

public class MessageUtils {
	private MessageUtils() {	
	}
	
	public static List<String> selectionMessage(SelectionShape selShape, SelectionData selData) {
		List<String> lines = new ArrayList<>();
		lines.add("--- [" + LogColor.GOLD + selShape.toString() + " Selection" + LogColor.RESET + "] ---------------------");
		for(String label : selData.extraDataLabels()) {
			String dataStr = "";
			Object data = selData.getExtraData(label);
			if(data != null) {
				dataStr = data.toString();
			}
			lines.add(LogColor.GOLD + label + ": " + LogColor.RESET + dataStr);
		}
		String offsetStr;
		if(selData.hasCustomOffset()) {
			offsetStr = selData.getOffset().toString();
		}else {
			offsetStr = selData.defualtOffsetLabel() + LogColor.GOLD + " (default)";
		}
		lines.add(LogColor.GOLD + "offset: " + LogColor.RESET + offsetStr);
		return lines;
	}
	
	
}
