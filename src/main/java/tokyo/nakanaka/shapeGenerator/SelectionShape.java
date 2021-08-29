package tokyo.nakanaka.shapeGenerator;

import java.util.Map;

import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.cuboid.CuboidSelectionStrategy;

public enum SelectionShape {
	CUBOID(new CuboidSelectionStrategy()),
	DIAMOND(null),
	SPHERE(null),
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
	
}
