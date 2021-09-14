package tokyo.nakanaka.shapeGenerator.command;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.LogicalConjunctRegion3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.MaxYRegion3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

@PublicAPI
public class MaxYCommand implements AdjustCommand {
	private GenerateCommand originalCmd;
	private GenerateCommand lastCmd;
	
	public MaxYCommand(GenerateCommand originalCmd, double maxY, boolean physics){
		this.originalCmd = originalCmd;
		Selection originalSel = originalCmd.getSelection();
		BoundRegion3D bound = originalSel.getBoundRegion3D();
		Region3D region = bound.getRegion3D();
		double ubx = bound.getUpperBoundX();
		double uby = maxY;
		double ubz = bound.getUpperBoundZ();
		double lbx = bound.getLowerBoundX();
		double lby = bound.getLowerBoundY();
		double lbz = bound.getLowerBoundZ();
		Region3D maxYReg = new MaxYRegion3D(maxY);
		Region3D newRegion = new LogicalConjunctRegion3D(region, maxYReg);
		BoundRegion3D newBound = new CuboidBoundRegion(newRegion, ubx, uby, ubz, lbx, lby, lbz);
		Selection sel = new Selection(originalSel.world(), newBound, originalSel.getOffset());
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
