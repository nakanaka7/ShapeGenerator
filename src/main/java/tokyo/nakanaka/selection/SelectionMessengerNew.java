package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.Vector3D;

public class SelectionMessengerNew {
	public void sendMessage(Logger logger, SelectionShape shape, 
			SelectionBuildingData selData, String defaultOffsetLabel) {
		logger.print(LogColor.LIGHT_PURPLE + shape.toString() + LogColor.RESET + " Selection");
		RegionBuildingData regionData = selData.getRegionData();
		List<String> labelList = regionData.getLabels();
		for(String label : labelList) {
			Object value = regionData.get(label);
			String valueStr = "";
			if(value != null) {
				valueStr = value.toString();
			}
			logger.print(LogColor.GOLD + label + ": " + LogColor.RESET + valueStr);
		}
		String offsetStr = defaultOffsetLabel;
		Vector3D offset = selData.getOffset();
		if(offset != null) {
			offsetStr = offset.toString();
		}
		logger.print(LogColor.GOLD + "offset: " + LogColor.RESET + offsetStr);
	}
}
