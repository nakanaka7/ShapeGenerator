package tokyo.nakanaka.command;

import tokyo.nakanaka.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.math.region3D.LogicalConjunctRegion3D;
import tokyo.nakanaka.math.region3D.MaxXRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.selection.Selection;

public class MaxXCommand implements AdjustCommand {
	private GenerateCommand originalCmd;
	private GenerateCommand lastCmd;
	
	public MaxXCommand(GenerateCommand originalCmd, double maxX, boolean physics){
		this.originalCmd = originalCmd;
		Selection originalSel = originalCmd.getSelection();
		BoundRegion3D bound = originalSel.getBoundRegion3D();
		Region3D region = bound.getRegion3D();
		double uby = bound.getUpperBoundY();
		double ubz = bound.getUpperBoundZ();
		double lbx = bound.getLowerBoundX();
		double lby = bound.getLowerBoundY();
		double lbz = bound.getLowerBoundZ();
		Region3D maxXReg = new MaxXRegion3D(maxX);
		Region3D newRegion = new LogicalConjunctRegion3D(region, maxXReg);
		BoundRegion3D newBound = new CuboidBoundRegion(newRegion, maxX, uby, ubz, lbx, lby, lbz);
		Selection sel = new Selection(originalSel.getWorld(), newBound, originalSel.getOffset());
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
