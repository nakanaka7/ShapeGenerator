package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class MirrorHelp implements BranchCommandHelp {
	private String usage = "/sg mirror x|y|z";
	private String description = "Mirror the generated blocks";

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
		return new String[]{"x|y|z"};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{"an axis to mirror"};
	}

}
