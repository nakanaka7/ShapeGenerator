package tokyo.nakanaka.shapeGenerator.command;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;

public class ShiftCommand implements AdjustCommand{
	private GenerateCommand originalCmd;
	private GenerateCommand lastCmd;
	
	public ShiftCommand(GenerateCommand originalCmd, Vector3D displacement, boolean physics) {
		this.originalCmd = originalCmd;
		Selection sel = originalCmd.getSelection().shift(displacement);
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