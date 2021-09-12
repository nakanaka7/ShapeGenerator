package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class MaxHelp implements CommandHelp {
	private String usage = "/sg max x|y|z <coordinate>";
	private String description = "Set max coordinate of the generated blocks";
	
	
	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + this.description;
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
