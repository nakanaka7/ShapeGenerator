package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

public interface CommandHelp {

	/**
	 * Returns a usage of the command
	 * @return a usage of the command
	 */
	String usage();

	/**
	 * Returns a description of the command;
	 * @return a description of the command;
	 */
	String description();

}
