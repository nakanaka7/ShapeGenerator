package tokyo.nakanaka.shapeGenerator.commandHelp;

import tokyo.nakanaka.logger.LogColor;

public class HelpHelp implements CommandHelp {
	private String usage = "/sg help [subcommand]";
	
	public String getUsage() {
		return this.usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + usage + ": " + LogColor.RESET + "Print the command help";
	}

}
