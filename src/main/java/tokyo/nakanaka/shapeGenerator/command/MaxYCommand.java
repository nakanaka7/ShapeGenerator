package tokyo.nakanaka.shapeGenerator.command;

import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.math.region3D.LogicalConjunctRegion3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.MaxYRegion3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.regionBound.CuboidBound;
import tokyo.nakanaka.shapeGenerator.math.regionBound.RegionBound;

@PrivateAPI
public class MaxYCommand implements AdjustCommand {
	private GenerateCommand originalCmd;
	private GenerateCommand lastCmd;
	
	public MaxYCommand(GenerateCommand originalCmd, double maxY, boolean physics){
		this.originalCmd = originalCmd;
		Selection originalSel = originalCmd.getSelection();
		RegionBound bound = originalSel.getBoundRegion3D();
		Region3D region = originalSel.region();
		double ubx = bound.upperBoundX();
		double uby = maxY;
		double ubz = bound.upperBoundZ();
		double lbx = bound.lowerBoundX();
		double lby = bound.lowerBoundY();
		double lbz = bound.lowerBoundZ();
		Region3D maxYReg = new MaxYRegion3D(maxY);
		Region3D newRegion = new LogicalConjunctRegion3D(region, maxYReg);
		RegionBound newBound = new CuboidBound(ubx, uby, ubz, lbx, lby, lbz);
		Selection sel = new Selection(originalSel.world(), originalSel.offset(), newRegion, newBound);
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
