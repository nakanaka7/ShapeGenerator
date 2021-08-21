package tokyo.nakanaka.shapeGenerator.commandHelp;

import tokyo.nakanaka.logger.LogColor;

public class GenrHelp implements CommandHelp {
	private String usage = "/sg genr <block>";

	public String getUsage() {
		return "/sg genr <block>";
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + "Generate block(s) in the selection";
	}

}
