package tokyo.nakanaka.command;

public interface UndoableCommand {
	void execute();
	void undo();
	void redo();
}
