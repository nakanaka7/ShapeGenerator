package tokyo.nakanaka.shapeGenerator.command;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.shapeGenerator.Selection;

@PublicAPI
public class ScaleCommand implements AdjustCommand{
	private GenerateCommand originalCmd;
	private GenerateCommand lastCmd;
	
	public ScaleCommand(GenerateCommand originalCmd, Axis axis, double factor, boolean physics) {
		this.originalCmd = originalCmd;
		Selection sel = originalCmd.getSelection().getScaledSelection(axis, factor);
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
