package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class ScaleHelp implements BranchCommandHelp {
	private String usage = "/sg scale x|y|z <factor>";
	private String description = "Change the scale of the generated block(s)";

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
		return new String[]{"x|y|z", "<factor>"};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{"an axis for scaling", "a factor for scaling"};
	}

	@Override
	public List<String> toMultipleLines() {
		List<String> lines = new ArrayList<>();
		lines.add("--- [" + LogColor.GOLD + "Help for " + LogColor.RESET + "/sg scale] ---------------------");
		lines.add(LogColor.GOLD + "Description: " + LogColor.RESET + this.description);
		lines.add(LogColor.GOLD + "Usage: " + LogColor.RESET + this.usage);
		lines.add(LogColor.GOLD + "Parameter: ");
		lines.add(LogColor.GOLD + "  x|y|z: " + LogColor.RESET + "an axis for scaling");
		lines.add(LogColor.GOLD + "  <factor>: " + LogColor.RESET + "a factor for scaling");
		return lines;
	}
	
}
