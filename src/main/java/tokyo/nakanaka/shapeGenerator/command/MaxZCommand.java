package tokyo.nakanaka.shapeGenerator.command;

import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.RegionBound;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBound;
import tokyo.nakanaka.shapeGenerator.math.region3D.LogicalConjunctRegion3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.MaxZRegion3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

@PrivateAPI
public class MaxZCommand implements AdjustCommand {
	private GenerateCommand originalCmd;
	private GenerateCommand lastCmd;
	
	public MaxZCommand(GenerateCommand originalCmd, double maxZ, boolean physics){
		this.originalCmd = originalCmd;
		Selection originalSel = originalCmd.getSelection();
		RegionBound bound = originalSel.getBoundRegion3D();
		Region3D region = originalSel.region();
		double ubx = bound.upperBoundX();
		double uby = bound.upperBoundY();
		double ubz = maxZ;
		double lbx = bound.lowerBoundX();
		double lby = bound.lowerBoundY();
		double lbz = bound.lowerBoundZ();
		Region3D maxZReg = new MaxZRegion3D(maxZ);
		Region3D newRegion = new LogicalConjunctRegion3D(region, maxZReg);
		RegionBound newBound = new CuboidBound(ubx, uby, ubz, lbx, lby, lbz);
		Selection sel = new Selection(originalSel.world(), originalSel.getOffset(), newRegion, newBound);
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
