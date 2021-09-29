package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class VersionHelp implements BranchCommandHelp {
	private String usage = "/sg version";
	private String description = "Print the version";

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
		return new String[]{};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{};
	}

}
