package tokyo.nakanaka.command;

public class DeleteCommand implements UndoableCommand{
	private GenerateCommand generateCmd;
	
	public DeleteCommand(GenerateCommand generateCmd) {
		this.generateCmd = generateCmd;
	}

	@Override
	public void execute() {
		this.generateCmd.undo();
	}

	@Override
	public void undo() {
		this.generateCmd.redo();
	}

	@Override
	public void redo() {
		this.generateCmd.undo();
	}

}
