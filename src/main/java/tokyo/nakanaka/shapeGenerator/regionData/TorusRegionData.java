package tokyo.nakanaka.shapeGenerator.regionData;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.math.Vector3D;

public class TorusRegionData implements RegionData {
	private Vector3D center;
	private Double radiusMain;
	private Double radiusSub;
	private Axis axis = Axis.Y;
	
	public Vector3D getCenter() {
		return center;
	}
	
	public void setCenter(Vector3D center) {
		this.center = center;
	}
	
	public Double getRadiusMain() {
		return radiusMain;
	}

	public void setRadiusMain(Double radiusMain) {
		this.radiusMain = radiusMain;
	}

	public Double getRadiusSub() {
		return radiusSub;
	}

	public void setRadiusSub(Double radiusSub) {
		this.radiusSub = radiusSub;
	}

	public Axis getAxis() {
		return axis;
	}
	
	public void setAxis(Axis axis) {
		this.axis = axis;
	}
	
}
