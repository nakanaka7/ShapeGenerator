package tokyo.nakanaka.shapeGenerator.command;

public interface UndoableCommand {
	void execute();
	void undo();
	void redo();
}
