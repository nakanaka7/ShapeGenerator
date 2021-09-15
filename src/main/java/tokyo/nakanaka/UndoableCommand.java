package tokyo.nakanaka;

import tokyo.nakanaka.annotation.PublicAPI;

@PublicAPI
public interface UndoableCommand {
	void execute();
	void undo();
	void redo();
}
