package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.List;

public interface CommandHelp {
	/**
	 * Return a single line which contains the information for the command.
	 * This is used by HelpCommandHandler class
	 * @return a single line which contains the information for the command
	 */
	String toSingleLine();
	/**
	 * Return multiple lines which contains the information for the command
	 * This is used by HelpCommandHandler class
	 * @return multiple lines which contains the information for the command
	 */
	List<String> toMultipleLines();
}
