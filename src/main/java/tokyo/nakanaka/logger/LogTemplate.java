package tokyo.nakanaka.logger;

public class LogTemplate {
	public static String ofLine(String title) {
		return LogColor.GRAY + "--- " 
			+ LogColor.RESET + LogColor.GOLD + "[" + title + "]"
			+ LogColor.RESET + LogColor.GRAY + " ---------------------";		
	}
	
	public static String ofKeyValue(String key, String value) {
		return LogColor.GOLD + key + ": "
			+ LogColor.RESET + LogColor.WHITE + value;
	}
	
}
