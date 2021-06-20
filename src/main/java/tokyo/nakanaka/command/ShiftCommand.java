package tokyo.nakanaka.command;

import tokyo.nakanaka.selection.Selection;

public class ShiftCommand implements MoveCommand{
	private GenerateCommand originalCmd;
	private GenerateCommand newCmd;
	
	public ShiftCommand(GenerateCommand originalCmd, int dx, int dy, int dz, boolean physics) {
		this.originalCmd = originalCmd;
		Selection sel = originalCmd.getSelection();
		sel.shift(dx, dy, dz);
		this.newCmd = new GenerateCommand(sel, originalCmd.getBlock(), physics);
	}

	@Override
	public void execute() {
		this.originalCmd.undo();
		this.newCmd.execute();
	}

	@Override
	public void undo() {
		this.newCmd.undo();
		this.originalCmd.redo();
	}

	@Override
	public void redo() {
		this.originalCmd.undo();
		this.newCmd.redo();
	}

	@Override
	public GenerateCommand getOriginalCommand() {
		return this.originalCmd;
	}

}
