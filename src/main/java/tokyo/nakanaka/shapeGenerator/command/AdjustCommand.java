package tokyo.nakanaka.shapeGenerator.command;

public interface AdjustCommand extends UndoableCommand{
	GenerateCommand getLastCommand();
}
