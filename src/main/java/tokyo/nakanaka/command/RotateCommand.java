package tokyo.nakanaka.command;

import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.selection.Selection;

public class RotateCommand implements AdjustCommand{
	private GenerateCommand originalCmd;
	private GenerateCommand lastCmd;
	
	public RotateCommand(GenerateCommand originalCmd, Axis axis, double degree, boolean physics) {
		this.originalCmd = originalCmd;
		LinearTransformation trans;
		switch(axis) {
		case X:
			trans = LinearTransformation.ofXRotation(degree);
			break;
		case Y:
			trans = LinearTransformation.ofYRotation(degree);
			break;
		case Z:
			trans = LinearTransformation.ofZRotation(degree);
			break;
		default:
			return;
		}
		Selection sel = originalCmd.getSelection().transform(trans);
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
