package tokyo.nakanaka.shapeGenerator.commandHelp;

import tokyo.nakanaka.logger.LogColor;

public class PhyHelp implements CommandHelp {
	private String usage = "/sg phy <true|false>";

	public String getUsage() {
		return this.usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + "Toggle physics option for generating blocks";
	}

}
