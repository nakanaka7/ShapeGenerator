package tokyo.nakanaka.logger.shapeGenerator;

import tokyo.nakanaka.logger.LogColor;

public class LogTemplate {
	public static String ofLine(String title) {
		return LogColor.WHITE + "--- " 
			+ LogColor.RESET + LogColor.GOLD + "[" + title + "]"
			+ LogColor.RESET + LogColor.WHITE + " ---------------------";		
	}
	
	public static String ofKeyValue(String key, String value) {
		return LogColor.GOLD + key + ": "
			+ LogColor.RESET + LogColor.WHITE + value;
	}
	
}
