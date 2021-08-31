package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.shapeGenerator.selectionStrategy.*;

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
	
}
