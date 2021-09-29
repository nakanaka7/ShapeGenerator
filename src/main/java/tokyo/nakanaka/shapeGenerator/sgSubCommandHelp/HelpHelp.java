package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class HelpHelp implements RootCommandHelp {
	private String usage = "/sg help [subcommand]";
	private String description = "Print the command help";

	@Override
	public String syntax() {
		return this.usage;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public List<String> toMultipleLines(){
		List<String> lines = new ArrayList<>();
		lines.add("--- [" + LogColor.GOLD + "Help for " + LogColor.RESET + "/sg help] ---------------------");
		lines.add(LogColor.GOLD + "Description: " + LogColor.RESET + this.description);
		lines.add(LogColor.GOLD + "Usage: " + LogColor.RESET + this.usage);
		lines.add(LogColor.GOLD + "Parameter: ");
		lines.add(LogColor.GOLD + "  [subcommand]: " + LogColor.RESET + "a subcommand to print the help");
		return lines;
	}
	
}
