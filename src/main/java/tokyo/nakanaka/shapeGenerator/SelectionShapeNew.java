package tokyo.nakanaka.shapeGenerator;

import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionStrategyNew.SelSubCommandHandlerNew;
import tokyo.nakanaka.shapeGenerator.selectionStrategyNew.SelectionStrategyNew;

@Deprecated
public enum SelectionShapeNew {
	CUBOID(null),
	DIAMOND(null),
	SPHERE(null),
	TORUS(null),
	LINE(null),
	TRIANGLE(null),
	TETRAHEDRON(null),
	REGULAR_POLYGON(null);
	
	private SelectionStrategyNew selStrtg;
	
	private SelectionShapeNew(SelectionStrategyNew selStrtg) {
		this.selStrtg = selStrtg;
	}
	
	/**
	 * Get SelSubCommandHandlerMap
	 * @return SelSubCommandHandlerMap
	 */
	public Map<String, SelSubCommandHandlerNew> getSelSubCommandHandlerMap() {
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
	
	/**
	 * Returns a selection from the selection data
	 * @param selData a selection data
	 * @return a selection from the selection data
	 * @throws IllegalArgumentException if the selection data cannot create a selection
	 */
	public Selection createSelection(SelectionData selData) {
		World world = selData.getWorld();
		RegionData regData = selData.getRegionData();
		BoundRegion3D boundReg = this.selStrtg.createBoundRegion3D(regData);
		Vector3D offset = selData.getOffsetOld();
		return new Selection(world, boundReg, offset);
	}
	
}
