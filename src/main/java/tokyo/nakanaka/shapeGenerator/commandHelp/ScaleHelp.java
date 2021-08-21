package tokyo.nakanaka.shapeGenerator.commandHelp;

import tokyo.nakanaka.logger.LogColor;

public class ScaleHelp implements CommandHelp {
	private String usage = "/sg scale <x|y|z> <factor>";
	
	public String getUsage() {
		return this.usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + "Change the scale of the generated block(s)";
	}
	
}
