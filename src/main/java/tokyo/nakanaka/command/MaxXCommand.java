package tokyo.nakanaka.command;

import tokyo.nakanaka.selection.Selection;

public class MaxXCommand implements AdjustCommand {
	private GenerateCommand originalCmd;
	private GenerateCommand lastCmd;
	
	public MaxXCommand(GenerateCommand originalCmd, double maxX, boolean physics){
		this.originalCmd = originalCmd;
		Selection originalSel = originalCmd.getSelection();
		Selection sel = originalSel.getXLimitedSelection(maxX);
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
