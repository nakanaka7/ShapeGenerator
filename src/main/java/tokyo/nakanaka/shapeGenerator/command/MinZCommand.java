package tokyo.nakanaka.shapeGenerator.command;

import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.LogicalConjunctRegion3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.MinZRegion3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

@PrivateAPI
public class MinZCommand implements AdjustCommand {
	private GenerateCommand originalCmd;
	private GenerateCommand lastCmd;
	
	public MinZCommand(GenerateCommand originalCmd, double minZ, boolean physics){
		this.originalCmd = originalCmd;
		Selection originalSel = originalCmd.getSelection();
		BoundRegion3D bound = originalSel.getBoundRegion3D();
		Region3D region = originalSel.region();
		double ubx = bound.upperBoundX();
		double uby = bound.upperBoundY();
		double ubz = bound.upperBoundZ();
		double lbx = bound.lowerBoundX();
		double lby = bound.lowerBoundY();
		double lbz = minZ;
		Region3D minZReg = new MinZRegion3D(minZ);
		Region3D newRegion = new LogicalConjunctRegion3D(region, minZReg);
		BoundRegion3D newBound = new CuboidBoundRegion(ubx, uby, ubz, lbx, lby, lbz);
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
