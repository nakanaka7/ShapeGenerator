package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.CommandHelp;

public class SelHelp implements CommandHelp {
	private String usage = "/sg sel <subcommand>";
	private String description = "Specify the selection";

	/**
	 * Get the usage
	 * @return the usage
	 */
	public String getUsage() {
		return usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + this.description;
	}

	@Override
	public List<String> toMultipleLines() {
		List<String> lines = new ArrayList<>();
		lines.add("--- [" + LogColor.GOLD + "Help for " + LogColor.RESET + "/sg sel] ---------------------");
		lines.add(LogColor.GOLD + "Description: " + LogColor.RESET + this.description);
		lines.add(LogColor.GOLD + "Usage: " + LogColor.RESET + this.usage);
		lines.add(LogColor.GOLD + "Parameter: ");
		lines.add(LogColor.GOLD + "  <subcommand>: " + LogColor.RESET + "subcommand");
		return lines;
	}

}
