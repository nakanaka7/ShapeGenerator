package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

public interface CommandHelp {

	/**
	 * Return the label of the command
	 * @return the label of the command
	 */
	String label();

	/**
	 * Returns a syntax of the command
	 * @return a syntax of the command
	 */
	String syntax();

	/**
	 * Returns parameter syntaxes
	 * @return parameter syntaxes
	 */
	String[] parameterSyntaxes();

	/**
	 * Returns a description of the command;
	 * @return a description of the command;
	 */
	String description();

}
