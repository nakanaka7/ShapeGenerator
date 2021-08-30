package tokyo.nakanaka.shapeGenerator;

import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.cuboid.CuboidSelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.sphere.SphereSelectionStrategy;

public enum SelectionShape {
	CUBOID(new CuboidSelectionStrategy()),
	DIAMOND(null),
	SPHERE(new SphereSelectionStrategy()),
	TORUS(null),
	LINE(null),
	TRIANGLE(null),
	TETRAHEDRON(null),
	REGULAR_POLYGON(null);
	
	private SelectionStrategy selStrtg;
	
	private SelectionShape(SelectionStrategy selStrtg) {
		this.selStrtg = selStrtg;
	}
	
	/**
	 * Get SelSubCommandHandlerMap
	 * @return SelSubCommandHandlerMap
	 */
	public Map<String, SelSubCommandHandler> getSelSubCommandHandlerMap() {
		return this.selStrtg.getSelSubCommandHandlerMap();
	}
	
	/**
	 * Get SelectionData
	 * @param world a world of the selection data
	 * @return SelectionData
	 */
	public SelectionData newSelectionData(World world) {
		RegionData regData = this.selStrtg.newRegionData();
		return new SelectionData(world, regData);
	}
	
}
