package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class VersionHelp implements CommandHelp {
	private String usage = "/sg version";
	private String description = "Print the version";
	
	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + this.description;
	}

	@Override
	public List<String> toMultipleLines() {
		List<String> lines = new ArrayList<>();
		lines.add("--- [" + LogColor.GOLD + "Help for " + LogColor.RESET + "/sg version] ---------------------");
		lines.add(LogColor.GOLD + "Description: " + LogColor.RESET + this.description);
		lines.add(LogColor.GOLD + "Usage: " + LogColor.RESET + this.usage);
		return lines;
	}

}
