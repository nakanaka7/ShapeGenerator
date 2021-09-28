package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class DelHelp implements BranchCommandHelp {
	private String usage = "/sg del [number]";
	private String description = "Delete the generated block(s)";

	@Override
	public String usage() {
		return this.usage;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public String[] parameterUsages(){
		return new String[]{"[number]"};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{"a number to delete generation(s)"};
	}

	@Override
	public List<String> toMultipleLines(){
		List<String> lines = new ArrayList<>();
		lines.add("--- [" + LogColor.GOLD + "Help for " + LogColor.RESET + "/sg del] ---------------------");
		lines.add(LogColor.GOLD + "Description: " + LogColor.RESET + this.description);
		lines.add(LogColor.GOLD + "Usage: " + LogColor.RESET + this.usage);
		lines.add(LogColor.GOLD + "Parameter: ");
		lines.add(LogColor.GOLD + "  [number]: " + LogColor.RESET + "a number to delete generation(s)");
		return lines;
	}

}
