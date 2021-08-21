package tokyo.nakanaka.shapeGenerator.commandHelp;

import tokyo.nakanaka.logger.LogColor;

public class RotHelp implements CommandHelp {
	private String usage = "/sg rot <x|y|z> <degree>";
	private String description = "Rotate the generated block(s)";
	
	public String getUsage() {
		return this.usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + this.description;
	}
	
}
