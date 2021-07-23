package tokyo.nakanaka.logger;

public class LogTemplate {
	public static String ofLine(String title) {
		return LogColor.GRAY + "--- " 
			+ LogColor.RESET + LogColor.GOLD + "[" + title + "]"
			+ LogColor.RESET + LogColor.GRAY + " ---------------------";		
	}
	
}
