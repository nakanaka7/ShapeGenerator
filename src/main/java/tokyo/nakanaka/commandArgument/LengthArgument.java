package tokyo.nakanaka.commandArgument;

import java.util.Arrays;
import java.util.List;

public class LengthArgument {
	
	public double onParse(String arg) {
		double length = Double.parseDouble(arg);
		if(length < 0) {
			throw new IllegalArgumentException();
		}
		return length;
	}
	
	public List<String> onTabComplete(String arg){
		return Arrays.asList("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0",
					"5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5", "10.0");
	}
}
