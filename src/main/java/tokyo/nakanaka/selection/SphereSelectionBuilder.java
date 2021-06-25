package tokyo.nakanaka.selection;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commandArgument.LengthArgument;
import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.Region3Ds;
import tokyo.nakanaka.math.region3D.SphereRegion3D;
import tokyo.nakanaka.world.World;

public class SphereSelectionBuilder extends AbstractSelectionBuilderNew{
	private Vector3D center;
	private double radius;
	private static final String CENTER = "center";
	private static final String RADIUS = "radius";
	private PositionArgument centerArg = new PositionArgument();
	private LengthArgument radiusArg = new LengthArgument();
	
	public SphereSelectionBuilder(World world) {
		super(world);
	}

	@Override
	public void onLeftClickBlock(Logger logger, BlockVector3D blockPos) {
		int x = blockPos.getX();
		int y = blockPos.getY();
		int z = blockPos.getZ();
		this.center = new Vector3D(x, y, z);
		this.radius = 0;
	}

	@Override
	public void onRightClickBlock(Logger logger, BlockVector3D blockPos) {
		int x = blockPos.getX();
		int y = blockPos.getY();
		int z = blockPos.getZ();
		Vector3D pos = new Vector3D(x, y, z);
		if(this.center == null) {
			logger.print(HEAD_ERROR + "Set center first");
		}
		double radius = Math.floor(pos.negate(this.center).getAbsolute()) + 0.5;
		this.radius = radius;
	}

	@Override
	public boolean onRegionCommand(Logger logger, BlockVector3D playerPos, String label, String[] args) {
		if(label.equals(CENTER)) {
			Vector3D center;
			try {
				center = this.centerArg.onParse(playerPos, args);
			}catch(IllegalArgumentException e) {
				return false;
			}
			this.center = center;
			return true;
		}else if(label.equals(RADIUS)) {
			if(args.length != 1) {
				return false;
			}
			double r;
			try {
				r = this.radiusArg.onParse(args[0]);
			}catch(IllegalArgumentException e) {
				return false;
			}	
			this.radius = r;
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public List<String> getRegionCommandLabelList() {
		return Arrays.asList(CENTER, RADIUS);
	}
	
	@Override
	public List<String> onRegionCommandTabComplete(String label, String[] args) {
		if(label.equals(CENTER)){
			return this.centerArg.onTabComplete(args);
		}else if(label.equals(RADIUS)) {
			if(args.length != 1) {
				return new ArrayList<>();
			}
			return this.radiusArg.onTabComplete(args[0]);
		}else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<String> getRegionStateLines() {
		String line1 = CENTER + ": ";
		if(this.center != null) {
			line1 += center.toString();
		}
		String line2 = RADIUS + ": " + this.radius;
		return Arrays.asList(line1, line2);
	}

	@Override
	public String getDefaultOffsetName() {
		return CENTER;
	}

	@Override
	public Vector3D getDefaultOffset() {
		return this.center;
	}
	
	@Override
	public BoundRegion3D buildRegion() {
		if(this.center == null) {
			throw new IllegalStateException();
		}
		Region3D region = new SphereRegion3D(this.radius);
		region = Region3Ds.shift(region, this.center);
		double ubx = center.getX() + radius;
		double uby = center.getY() + radius;
		double ubz = center.getZ() + radius;
		double lbx = center.getX() - radius;
		double lby = center.getY() - radius;
		double lbz = center.getZ() - radius;
		return new BoundRegion3D(region, ubx, uby, ubz, lbx, lby, lbz);
	}
	
}
