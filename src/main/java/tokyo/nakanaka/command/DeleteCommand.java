package tokyo.nakanaka.command;

public class DeleteCommand implements UndoableCommand{
	private GenerateCommand[] generateCmds;
	
	public DeleteCommand(GenerateCommand... generateCmds) {
		this.generateCmds = generateCmds;
	}

	@Override
	public void execute() {
		for(GenerateCommand cmd : this.generateCmds) {
			cmd.undo();
		}
	}

	@Override
	public void undo() {
		for(GenerateCommand cmd : this.generateCmds) {
			cmd.redo();
		}
	}

	@Override
	public void redo() {
		for(GenerateCommand cmd : this.generateCmds) {
			cmd.undo();
		}
	}

}
