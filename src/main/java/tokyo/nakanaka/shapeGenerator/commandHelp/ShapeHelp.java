package tokyo.nakanaka.shapeGenerator.commandHelp;

import tokyo.nakanaka.logger.LogColor;

public class ShapeHelp implements CommandHelp {
	private String usage = "/sg shape <type>";
	private String description = "Set selection shape";
	
	public String getUsage() {
		return usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + this.description;
	}

}
