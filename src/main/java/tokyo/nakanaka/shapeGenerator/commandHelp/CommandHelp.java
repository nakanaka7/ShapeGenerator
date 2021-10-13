package tokyo.nakanaka.shapeGenerator.commandHelp;

public interface CommandHelp {

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
