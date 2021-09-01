package tokyo.nakanaka.shapeGenerator;

import java.util.List;
import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.CuboidSelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.DiamondSelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.LineSelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.RegularPolygonSelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SphereSelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.TetrahedronSelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.TorusSelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.TriangleSelectionStrategy;

public enum SelectionShape {
	CUBOID(new CuboidSelectionStrategy()),
	DIAMOND(new DiamondSelectionStrategy()),
	SPHERE(new SphereSelectionStrategy()),
	TORUS(new TorusSelectionStrategy()),
	LINE(new LineSelectionStrategy()),
	TRIANGLE(new TriangleSelectionStrategy()),
	TETRAHEDRON(new TetrahedronSelectionStrategy()),
	REGULAR_POLYGON(new RegularPolygonSelectionStrategy());
	
	private SelectionStrategy selStrtg;
	
	private SelectionShape(SelectionStrategy selStrtg) {
		this.selStrtg = selStrtg;
	}
	
	/**
	 * Get SelSubCommandHandlerMap
	 * @return SelSubCommandHandlerMap
	 */
	public Map<String, SelSubCommandHandler> getSelSubCommandHandlerMap() {
		return this.selStrtg.selSubCommandHandlerMap();
	}
	
	/**
	 * Get SelectionData
	 * @param world a world of the selection data
	 * @return SelectionData
	 */
	public SelectionData newSelectionData(World world) {
		List<String> keyList = this.selStrtg.regionKeyList();
		String[] regionDataKeys = keyList.toArray(new String[keyList.size()]);
		return new SelectionData(world, this.selStrtg.defaultOffsetKey(), regionDataKeys);
	}
	
}
