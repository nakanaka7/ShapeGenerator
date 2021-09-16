package tokyo.nakanaka.shapeGenerator.command;

import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.annotation.PrivateAPI;

@PrivateAPI
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
