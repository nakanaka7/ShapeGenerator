package tokyo.nakanaka.shapeGenerator.commandArgument;

import java.util.Arrays;
import java.util.List;

public class CoordinateCommandArgument {
	public int onParsingInt(String arg, int offset) {
		int s;
		try {
			s = Integer.parseInt(arg);
		}catch (IllegalArgumentException e) {
			if (!arg.startsWith("~")) {
				throw new IllegalArgumentException();
			}else {
				arg = arg.substring(1);
				int t;
				if(arg.isEmpty()){
					t = 0;
				}else{
					t = Integer.parseInt(arg);
				}
				s = offset + t;
			}
		}
		return s;
	}
	
	public double onParsingDouble(String arg, int offset) {
		double s;
		try {
			s = Double.parseDouble(arg);
		}catch (IllegalArgumentException e) {
			if (!arg.startsWith("~")) {
				throw new IllegalArgumentException();
			}else {
				arg = arg.substring(1);
				double t;
				if(arg.isEmpty()){
					t = 0;
				}else{
					t = Double.parseDouble(arg);
				}
				s = offset + t;
			}
		}
		return s;
	}
	
	public List<String> onTabComplete(String arg) {
		return Arrays.asList("~");
	}
}