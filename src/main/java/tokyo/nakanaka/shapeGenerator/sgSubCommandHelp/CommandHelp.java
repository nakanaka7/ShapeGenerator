package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

public interface CommandHelp {

	/**
	 * Returns a syntax of the command
	 * @return a syntax of the command
	 */
	String syntax();

	/**
	 * Returns a description of the command;
	 * @return a description of the command;
	 */
	String description();

}
