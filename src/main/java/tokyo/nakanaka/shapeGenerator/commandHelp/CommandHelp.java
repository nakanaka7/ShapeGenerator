package tokyo.nakanaka.shapeGenerator.commandHelp;

public interface CommandHelp {
	/**
	 * Return a single line which contains the information for the command.
	 * This is used by HelpCommandHandler class
	 * @return a single line which contains the information for the command
	 */
	String toSingleLine();
}
