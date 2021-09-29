package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class GenrHelp implements BranchCommandHelp {
	private String usage = "/sg genr <block>";
	private String description = "Generate block(s) in the selection";

	@Override
	public String syntax() {
		return "/sg genr <block>";
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public String[] parameterSyntaxes(){
		return new String[]{"<block>"};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{"the block to generate"};
	}

}
