package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.List;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.CommandHelp;

public class ShiftHelp implements CommandHelp {
	private String usage = "/sg shift <direction> <length>";
	private String description = "Shift the generated blocks";
	
	public String getUsage() {
		return this.usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + this.description;
	}

	@Override
	public List<String> toMultipleLines() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
