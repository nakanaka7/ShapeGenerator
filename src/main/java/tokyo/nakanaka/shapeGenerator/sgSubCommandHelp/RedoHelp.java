package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class RedoHelp implements BranchCommandHelp {
	private String usage = "/sg redo [number]";
	private String description = "Redo block changing command(s)";

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
		return new String[]{"[number]"};
	}

	@Override
	public String[] parameterDescriptions(){
		return new String[]{"a number to redo generation(s)"};
	}

}
