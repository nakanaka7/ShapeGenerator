package tokyo.nakanaka.shapeGenerator.commandHelp;

import tokyo.nakanaka.logger.LogColor;

public class UndoHelp implements CommandHelp {
	private String usage = "/sg undo [number]";
	private String description = "Undo block changing command(s)";
	
	public String getUsage() {
		return this.usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + this.description;
	}
	
}
