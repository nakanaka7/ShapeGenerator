package tokyo.nakanaka.shapeGenerator.commandHelp;

import tokyo.nakanaka.logger.LogColor;

public class MirrorHelp implements CommandHelp {
	private String usage = "/sg mirror <x|y|z>";
	private String description = "Mirror the generated blocks";
	
	public String getUsage() {
		return this.usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + this.description;
	}
	
}
