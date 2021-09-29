package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class ShiftHelp implements BranchCommandHelp {
	private String usage = "/sg shift <direction> <distance>";
	private String description = "Shift the generated blocks";

	@Override
	public String syntax() {
		return this.usage;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public String[] parameterSyntaxes(){
		return new String[]{"<direction>", "<distance>"};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{"the direction to shift", "distance of shift"};
	}

}
