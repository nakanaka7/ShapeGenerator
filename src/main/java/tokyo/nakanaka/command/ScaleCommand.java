package tokyo.nakanaka.command;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.selection.Selection;

public class ScaleCommand implements AdjustCommand{
	private GenerateCommand originalCmd;
	private GenerateCommand lastCmd;
	
	public ScaleCommand(GenerateCommand originalCmd, Axis axis, double factor, boolean physics) {
		this.originalCmd = originalCmd;
		LinearTransformation trans;
		switch(axis) {
		case X:
			trans = LinearTransformation.ofXScale(factor);
			break;
		case Y:
			trans = LinearTransformation.ofYScale(factor);
			break;
		case Z:
			trans = LinearTransformation.ofZScale(factor);
			break;
		default:
			return;
		}
		Selection sel = originalCmd.getSelection().getTransformedSelection(trans);
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
