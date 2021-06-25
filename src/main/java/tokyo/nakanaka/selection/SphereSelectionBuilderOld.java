package tokyo.nakanaka.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static tokyo.nakanaka.logger.LogConstant.*;
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

public class SphereSelectionBuilderOld implements SelectionBuilder{
	private World world;
	private Vector3D center;
	private double radius;
	private Vector3D offset;
	private static final String CENTER = "center";
	private static final String RADIUS = "radius";
	private static final String OFFSET = "offset";
	private PositionArgument centerArg = new PositionArgument();
	private LengthArgument radiusArg = new LengthArgument();
	private PositionArgument offsetArg = new PositionArgument();
	
	public SphereSelectionBuilderOld(World world) {
		this.world = world;
	}
	
	@Override
	public World getWorld() {
		return this.world;
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
	public boolean onCommand(Logger logger, BlockVector3D playerPos, String label, String[] args) {
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
		}else if(label.equals(OFFSET)){
			Vector3D offset;
			try {
				offset = this.offsetArg.onParse(playerPos, args);
			}catch(IllegalArgumentException e) {
				return false;
			}
			this.offset = offset;
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public List<String> onLabelList() {
		return Arrays.asList(CENTER, RADIUS, OFFSET);
	}
	
	@Override
	public List<String> onTabComplete(String label, String[] args) {
		if(label.equals(CENTER)){
			return this.centerArg.onTabComplete(args);
		}else if(label.equals(RADIUS)) {
			if(args.length != 1) {
				return new ArrayList<>();
			}
			return this.radiusArg.onTabComplete(args[0]);
		}else if(label.equals(OFFSET)) {
			return this.offsetArg.onTabComplete(args);
		}else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<String> getStateLines() {
		String line1 = CENTER + ": ";
		if(this.center != null) {
			line1 += center.toString();
		}
		String line2 = RADIUS + ": " + this.radius;
		String line3 = OFFSET + ": ";
		if(offset != null) {
			line3 += offset.toString();
		}else {
			line3 += "= center";
		}
		return Arrays.asList(line1, line2, line3);
	}

	@Override
	public Selection build() {
		Region3D region = new SphereRegion3D(this.radius);
		if(this.center == null) {
			throw new IllegalStateException();
		}
		region = Region3Ds.shift(region, this.center);
		double ubx = center.getX() + radius;
		double uby = center.getY() + radius;
		double ubz = center.getZ() + radius;
		double lbx = center.getX() - radius;
		double lby = center.getY() - radius;
		double lbz = center.getZ() - radius;
		BoundRegion3D boundRegion = new BoundRegion3D(region, ubx, uby, ubz, lbx, lby, lbz);
		if(this.offset == null) {
			this.offset = center;
		}
		return new Selection(this.world, boundRegion, this.offset);
	}

}
