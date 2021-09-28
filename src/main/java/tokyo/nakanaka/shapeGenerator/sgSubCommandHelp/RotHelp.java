package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class RotHelp implements BranchCommandHelp {
	private String usage = "/sg rot x|y|z <degree>";
	private String description = "Rotate the generated block(s)";

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
		return new String[]{"x|y|z", "<degree>"};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{"an axis for rotating", "a degree for rotating"};
	}

}
