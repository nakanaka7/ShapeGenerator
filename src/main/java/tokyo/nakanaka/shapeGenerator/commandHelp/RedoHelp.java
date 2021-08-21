package tokyo.nakanaka.shapeGenerator.commandHelp;

import tokyo.nakanaka.logger.LogColor;

public class RedoHelp implements CommandHelp {
	private String usage = "/sg redo [number]";
	private String description = "Redo block changing command(s)";
	
	public String getUsage() {
		return this.usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + this.description;
	}
	
}
