package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class MinHelp implements BranchCommandHelp {
	private String usage = "/sg min x|y|z <coordinate>";
	private String description = "Set min coordinate of the generated blocks";

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
		return new String[]{"x|y|z", "<coordinate>"};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{"axis", "minimum coordinate of the generation"};
	}

}
