package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.LogDesignColor;
import tokyo.nakanaka.logger.LogTemplate;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;

public class SelectionMessenger {
	public void printSelection(Logger logger, SelectionShape shape, 
			SelectionBuildingData selData, String defaultOffsetLabel) {
		logger.print(LogTemplate.ofLine(shape.toString() + " Selection"));
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
	
	public void printClickDescription(Logger logger, SelectionStrategy strategy) {
		logger.print(LogDesignColor.NORMAL + "Left click: " + strategy.getLeftClickDescription()
		+ " / " + "Right click: " + strategy.getRightClickDescription());
	}
}
