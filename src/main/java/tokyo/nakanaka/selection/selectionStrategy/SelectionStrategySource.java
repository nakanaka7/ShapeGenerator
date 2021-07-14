package tokyo.nakanaka.selection.selectionStrategy;

import tokyo.nakanaka.selection.SelectionShape;

public class SelectionStrategySource {
	public SelectionStrategy get(SelectionShape shape) {
		switch(shape) {
		case CUBOID:
			return new CuboidSelectionStrategy();
		case LINE:
			return new LineSelectionStrategy();
		case REGULAR_POLYGON:
			return new RegularPolygonSelectionStrategy();
		case SPHERE:
			return new SphereSelectionStrategy();
		case TETRAHEDRON:
			return new TetrahedronSelectionStrategy();
		case TORUS:
			return new TorusSelectionStrategy();
		case TRIANGLE:
			return new TriangleSelectionStrategy();
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
