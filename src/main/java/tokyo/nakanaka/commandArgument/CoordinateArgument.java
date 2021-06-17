package tokyo.nakanaka.commandArgument;

import java.util.Arrays;
import java.util.List;

public class CoordinateArgument {
	public int onParsing(String arg, int offset) {
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
	
	public List<String> onTabComplete(String arg) {
		return Arrays.asList("~");
	}
}
