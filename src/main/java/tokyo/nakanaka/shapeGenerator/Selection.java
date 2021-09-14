package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.World;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;

@PublicAPI
public class Selection {
	private World world;
	private BoundRegion3D boundReg;
	private Vector3D offset;
	
	public Selection(World world, BoundRegion3D boundReg, Vector3D offset) {
		this.world = world;
		this.boundReg = boundReg;
		this.offset = offset;
	}
		
	public World getWorld() {
		return world;
	}
	
	public BlockRegion3D getBlockRegion3D() {
		return this.boundReg.toBlockRegion3D();
	}
	
	public BoundRegion3D getBoundRegion3D() {
		return boundReg;
	}
	
	public Vector3D getOffset() {
		return offset;
	}

	public Selection shift(Vector3D displacement) {
		BoundRegion3D region = this.boundReg.createShiftedRegion(displacement);
		Vector3D offset = this.offset.add(displacement);
		return new Selection(world, region, offset);
	}
	
	public Selection getScaledSelection(Axis axis, double factor) {
		BoundRegion3D newRegion = this.boundReg.createScaledRegion(axis, factor, this.offset);
		return new Selection(this.world, newRegion, this.offset);
	}
	
	public Selection getMirroedSelection(Axis axis) {
		BoundRegion3D newRegion = this.boundReg.createMirroredRegion(axis, this.offset);
		return new Selection(this.world, newRegion, this.offset);
	}
	
	public Selection getRotatedSelection(Axis axis, double degree) {
		BoundRegion3D newRegion = this.boundReg.createRotatedRegion(axis, degree, this.offset);
		return new Selection(this.world, newRegion, this.offset);
	}
	
	public Selection getXLimitedSelection(double maxX) {
		return null;
	}

}
