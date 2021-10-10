package tokyo.nakanaka.shapeGenerator.message;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionShape;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {
	private MessageUtils() {	
	}

	/**
	 * Returns a String, "--- [(title)] ---------------------"
	 * @param title a title
	 * @return a String, "--- [(title)] ---------------------"
	 */
	public static String title(String title) {
		return "--- [" + title + LogColor.RESET + "] ---------------------";
	}

	public static List<String> selectionMessage(LogColor mainColor, SelectionShape selShape, SelectionData selData) {
		List<String> lines = new ArrayList<>();
		String shape = selShape.name();
		shape = shape.substring(0,1) + shape.substring(1).toLowerCase();
		lines.add(title(mainColor + shape + " Selection"));
		for(String label : selData.extraDataLabels()) {
			String dataStr = "";
			Object data = selData.getExtraData(label);
			if(data != null) {
				dataStr = data.toString();
			}
			lines.add(mainColor + label + ": " + LogColor.RESET + dataStr);
		}
		String offsetStr;
		if(selData.hasCustomOffset()) {
			offsetStr = selData.getOffset().toString();
		}else {
			offsetStr = selData.defualtOffsetLabel() + mainColor + " (default)";
		}
		lines.add(mainColor + "offset: " + LogColor.RESET + offsetStr);
		return lines;
	}

}
