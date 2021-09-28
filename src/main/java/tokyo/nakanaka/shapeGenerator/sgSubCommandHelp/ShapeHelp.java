package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class ShapeHelp implements BranchCommandHelp {
	private String usage = "/sg shape <type>";
	private String description = "Set selection shape";

	@Override
	public String usage() {
		return usage;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public String[] parameterUsages(){
		return new String[]{"<type>"};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{"a shape type"};
	}

}
