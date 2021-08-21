package tokyo.nakanaka.shapeGenerator.commandHelp;

import tokyo.nakanaka.logger.LogColor;

public class DelHelp implements CommandHelp {
	private String usage = "/sg del [number]";
	
	public String getUsage() {
		return this.usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + usage + ": " + LogColor.RESET + "Delete the generated block(s)";
	}

}
