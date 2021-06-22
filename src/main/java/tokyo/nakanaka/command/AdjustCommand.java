package tokyo.nakanaka.command;

public interface AdjustCommand extends UndoableCommand{
	GenerateCommand getLastCommand();
}
