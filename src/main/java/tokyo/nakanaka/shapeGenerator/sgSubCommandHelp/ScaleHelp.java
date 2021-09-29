package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class ScaleHelp implements BranchCommandHelp {
	private String usage = "/sg scale x|y|z <factor>";
	private String description = "Change the scale of the generated block(s)";

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
		return new String[]{"x|y|z", "<factor>"};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{"an axis for scaling", "a factor for scaling"};
	}

}
