package tokyo.nakanaka.shapeGenerator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import tokyo.nakanaka.logger.LogColor;

public class Utils {
	/**
	 * Returns descriptive lines for the selection data
	 * @param selData the selection data
	 * @return descriptive lines for the selection data
	 */
	public static List<String> getSelectionMessageLines(SelectionData selData){
		List<String> list = new ArrayList<>();
		LinkedHashMap<String, Object> regDataMap = selData.getRegionDataMap();
		for(Entry<String, Object> e : regDataMap.entrySet()) {
			list.add(LogColor.GOLD + e.getKey() + ": " + LogColor.RESET + e.getValue());
		}
		list.add(LogColor.GOLD + "offset: " + LogColor.RESET + selData.getOffset());
		return list;
	}
	
}
