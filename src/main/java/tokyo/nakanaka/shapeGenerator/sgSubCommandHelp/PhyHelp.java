package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class PhyHelp implements BranchCommandHelp {
	private String usage = "/sg phy true|false";
	private String description = "Toggle physics option for generating blocks";

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
		return new String[]{"true|false"};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{"an option for physics"};
	}

}
