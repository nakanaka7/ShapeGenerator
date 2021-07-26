package tokyo.nakanaka.logger;

import tokyo.nakanaka.Pair;

public class LogTemplate {
	public static String ofLine(String title) {
		return LogColor.GRAY + "--- " 
			+ LogColor.RESET + LogColor.GOLD + "[" + title + "]"
			+ LogColor.RESET + LogColor.GRAY + " ---------------------";		
	}
	
	public static String ofPair(Pair<?, ?> pair) {
		return LogColor.GOLD + pair.getFirst().toString() + ": " 
			+ LogColor.RESET + LogColor.WHITE + pair.getSecond();
	}
	
}
