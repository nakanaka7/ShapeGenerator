package tokyo.nakanaka.selection.cuboid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.selection.AbstractSelectionBuilder;
import tokyo.nakanaka.world.World;

public class CuboidSelectionBuilder extends AbstractSelectionBuilder{
	private CuboidRegionBuilder cuboidBuilder = new CuboidRegionBuilder();
	private static final String POS1 = "pos1";
	private static final String POS2 = "pos2";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String LENGTH = "length";
	private PositionArgument pos1Arg = new PositionArgument();
	private PositionArgument pos2Arg = new PositionArgument();
	
	public CuboidSelectionBuilder(World world) {
		super(world);
	}
	
	@Override
	public void onLeftClickBlock(Logger logger, BlockVector3D blockPos) {
		this.cuboidBuilder.setPos1(new Vector3D(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
	}

	@Override
	public void onRightClickBlock(Logger logger, BlockVector3D blockPos) {
		this.cuboidBuilder.setPos1(new Vector3D(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
	}
	
	@Override
	public boolean onRegionCommand(Logger logger, BlockVector3D playerPos, String label, String[] args) {
		if(label.equals(POS1)) {
			Vector3D pos1;
			try {
				pos1 = this.pos1Arg.onParse(playerPos, args);
			}catch(IllegalArgumentException e) {
				return false;
			}
			this.cuboidBuilder.setPos1(pos1);
			return true;
		}else if(label.equals(POS2)) {
			Vector3D pos2;
			try {
				pos2 = this.pos2Arg.onParse(playerPos, args);
			}catch(IllegalArgumentException e) {
				return false;
			}
			this.cuboidBuilder.setPos2(pos2);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<String> getRegionCommandLabelList() {
		return Arrays.asList(POS1, POS2);
	}
	
	@Override
	public List<String> onRegionCommandTabComplete(String label, String[] args) {
		if(label.equals(POS1)){
			return this.pos1Arg.onTabComplete(args);
		}else if(label.equals(POS2)) {
			return this.pos2Arg.onTabComplete(args);
		}else {
			return new ArrayList<>();
		}
	}
	
	@Override
	public List<String> getRegionStateLines() {
		Vector3D pos1 = this.cuboidBuilder.getPos1();
		Vector3D pos2 = this.cuboidBuilder.getPos2();
		String line1 = POS1 + ": ";
		if(pos1 != null) {
			line1 += pos1.toString();
		}
		String line2 = POS2 + ": ";
		if(pos2 != null) {
			line2 += pos2.toString();
		}
		String line3 = WIDTH + ": ";
		String line4 = HEIGHT + ": ";
		String line5 = LENGTH + ": ";
		if(pos1 != null && pos2 != null) {
			double width = pos2.getX() - pos1.getX();
			double height = pos2.getY() - pos1.getY();
			double length = pos2.getZ() - pos1.getZ();
			line3 += String.valueOf(width);
			line4 += String.valueOf(height);
			line5 += String.valueOf(length);
		}
		return Arrays.asList(line1, line2, line3, line4, line5);
	}
	
	@Override
	public String getDefaultOffsetName() {
		return POS1;
	}

	@Override
	public Vector3D getDefaultOffset() {
		return this.cuboidBuilder.getPos1();
	}
	
	@Override
	public BoundRegion3D buildRegion() {
		return this.cuboidBuilder.buildRegion();
	}

}
