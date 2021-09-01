package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.CommandHelp;

public class MinxHelp implements CommandHelp {
	private String usage = "/sg minx <value>";
	private String description = "Set min x of the generated blocks";
	
	public String getUsage() {
		return this.usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + this.description;
	}

	@Override
	public List<String> toMultipleLines() {
		List<String> lines = new ArrayList<>();
		lines.add("--- [" + LogColor.GOLD + "Help for " + LogColor.RESET + "/sg minx] ---------------------");
		lines.add(LogColor.GOLD + "Description: " + LogColor.RESET + this.description);
		lines.add(LogColor.GOLD + "Usage: " + LogColor.RESET + this.usage);
		lines.add(LogColor.GOLD + "Parameter: ");
		lines.add(LogColor.GOLD + "  <value>: " + LogColor.RESET + "x coordinate");
		return lines;
	}
	
}