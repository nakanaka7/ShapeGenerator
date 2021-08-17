package tokyo.nakanaka.command;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.selection.Selection;

public class MirrorCommand implements AdjustCommand {
	private GenerateCommand originalCmd;
	private GenerateCommand lastCmd;
	
	public MirrorCommand(GenerateCommand originalCmd, Axis axis, boolean physics) {
		this.originalCmd = originalCmd;
		Selection sel = originalCmd.getSelection().getMirroedSelection(axis);
		this.lastCmd = new GenerateCommand(sel, originalCmd.getBlock(), physics);
	}
	
	@Override
	public void execute() {
		this.originalCmd.undo();
		this.lastCmd.execute();
	}

	@Override
	public void undo() {
		this.lastCmd.undo();
		this.originalCmd.redo();
	}

	@Override
	public void redo() {
		this.originalCmd.undo();
		this.lastCmd.redo();
	}

	@Override
	public GenerateCommand getLastCommand() {
		return this.lastCmd;
	}
	
}
