package tokyo.nakanaka.shapeGenerator.commandHelp;

import tokyo.nakanaka.logger.LogColor;

public class SelHelp implements CommandHelp {
	private String usage = "/sg sel <subcommand>";
	private String description = "Specify the selection";

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + this.description;
	}

}
