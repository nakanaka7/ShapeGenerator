package tokyo.nakanaka.shapeGenerator.command;

import tokyo.nakanaka.annotation.PublicAPI;

@PublicAPI
public interface UndoableCommand {
	void execute();
	void undo();
	void redo();
}
