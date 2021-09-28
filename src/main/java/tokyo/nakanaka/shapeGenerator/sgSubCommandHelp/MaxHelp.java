package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class MaxHelp implements BranchCommandHelp {
	private String usage = "/sg max x|y|z <coordinate>";
	private String description = "Set max coordinate of the generated blocks";

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
		return new String[]{"x|y|z", "<coordinate>"};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{"axis", "maximum coordinate of the generation"};
	}

	@Override
	public List<String> toMultipleLines() {
		List<String> lines = new ArrayList<>();
		lines.add("--- [" + LogColor.GOLD + "Help for " + LogColor.RESET + "/sg max] ---------------------");
		lines.add(LogColor.GOLD + "Description: " + LogColor.RESET + this.description);
		lines.add(LogColor.GOLD + "Usage: " + LogColor.RESET + this.usage);
		lines.add(LogColor.GOLD + "Parameter: ");
		lines.add(LogColor.GOLD + "  x|y|z: " + LogColor.RESET + "axis");
		lines.add(LogColor.GOLD + "  <coodinate>: " + LogColor.RESET + "maximum coordinate of the generation");
		return lines;
	}

}
