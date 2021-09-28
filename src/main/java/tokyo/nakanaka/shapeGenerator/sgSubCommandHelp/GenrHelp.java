package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class GenrHelp implements BranchCommandHelp {
	private String usage = "/sg genr <block>";
	private String description = "Generate block(s) in the selection";

	@Override
	public String usage() {
		return "/sg genr <block>";
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public String[] parameterUsages(){
		return new String[]{"<block>"};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{"the block to generate"};
	}

	@Override
	public List<String> toMultipleLines(){
		List<String> lines = new ArrayList<>();
		lines.add("--- [" + LogColor.GOLD + "Help for " + LogColor.RESET + "/sg genr] ---------------------");
		lines.add(LogColor.GOLD + "Description: " + LogColor.RESET + this.description);
		lines.add(LogColor.GOLD + "Usage: " + LogColor.RESET + this.usage);
		lines.add(LogColor.GOLD + "Parameter: ");
		lines.add(LogColor.GOLD + "  <block>: " + LogColor.RESET + "the block to generate");
		return lines;
	}

}
