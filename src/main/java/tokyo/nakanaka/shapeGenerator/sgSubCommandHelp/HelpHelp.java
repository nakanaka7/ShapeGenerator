package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class HelpHelp implements CommandHelp {
	private String usage = "/sg help [subcommand]";
	private String description = "Print the command help";
	
	public String getUsage() {
		return this.usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + usage + ": " + LogColor.RESET + this.description;
	}
	
	@Override
	public List<String> toMultipleLines(){
		List<String> lines = new ArrayList<>();
		lines.add("--- [" + LogColor.GOLD + "Help for " + LogColor.RESET + "/sg del] ---------------------");
		lines.add(LogColor.GOLD + "Description: " + LogColor.RESET + this.description);
		lines.add(LogColor.GOLD + "Usage: " + LogColor.RESET + this.usage);
		lines.add(LogColor.GOLD + "Parameter: ");
		lines.add(LogColor.GOLD + "  [subcommand]: " + LogColor.RESET + "a subcommand to print the help");
		return lines;
	}
	
}
