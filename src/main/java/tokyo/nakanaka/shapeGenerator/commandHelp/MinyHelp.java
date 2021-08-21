package tokyo.nakanaka.shapeGenerator.commandHelp;

import tokyo.nakanaka.logger.LogColor;

public class MinyHelp implements CommandHelp {
	private String usage = "/sg miny <value>";

	public String getUsage() {
		return this.usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + "Set min y of the generated blocks";
	}
	
}
