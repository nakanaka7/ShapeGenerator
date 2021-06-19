package tokyo.nakanaka.command;

public interface MoveCommand extends UndoableCommand{
	GenerateCommand getOriginalCommand();
}
