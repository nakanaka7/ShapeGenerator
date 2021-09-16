package tokyo.nakanaka.shapeGenerator.command;

import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.annotation.PrivateAPI;

/**
 * A command which adjust existing block generation which is done by GenerateCommand.
 * The getLastCommand() method will returns GenerateCommand that is responsible for the adjusted generation.
 * The original generation must be deleted in execute() method internally. 
 */
@PrivateAPI
public interface AdjustCommand extends UndoableCommand{
	/**
	 * Returns GenerateCommand which is responsible for adjusted generation
	 * @return GenerateCommand which is responsible for adjusted generation
	 */
	GenerateCommand getLastCommand();
}
