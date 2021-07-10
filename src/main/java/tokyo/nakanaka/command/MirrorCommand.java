package tokyo.nakanaka.command;

import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.selection.Selection;

public class MirrorCommand implements AdjustCommand {
	private GenerateCommand originalCmd;
	private GenerateCommand lastCmd;
	
	public MirrorCommand(GenerateCommand originalCmd, Axis axis, boolean physics) {
		this.originalCmd = originalCmd;
		LinearTransformation trans;
		switch(axis) {
		case X:
			trans = LinearTransformation.X_MIRROR;
			break;
		case Y:
			trans = LinearTransformation.Y_MIRROR;
			break;
		case Z:
			trans = LinearTransformation.Z_MIRROR;
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
