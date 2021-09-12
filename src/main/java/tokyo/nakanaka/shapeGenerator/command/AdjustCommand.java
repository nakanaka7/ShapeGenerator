package tokyo.nakanaka.shapeGenerator.command;

import tokyo.nakanaka.annotation.PrivateAPI;

@PrivateAPI
public interface AdjustCommand extends UndoableCommand{
	GenerateCommand getLastCommand();
}
