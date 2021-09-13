package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.annotation.PublicAPI;

@PublicAPI
public enum SelectionShape {
	CUBOID("cuboid"),
	DIAMOND("diamond"),
	SPHERE("sphere"),
	CYLINDER("cylinder"),
	CONE("cone"),
	TORUS("torus"),
	LINE("line"),
	TRIANGLE("triangle"),
	TETRAHEDRON("tetrahedron"),
	REGULAR_POLYGON("regular_polygon"),
	HOLLOW_SPHERE("hollow_sphere"),
	HOLLOW_CYLINDER("hollow_cylinder"),
	HOLLOW_REGULAR_POLYGON("hollow_regular_polygon");
	
	private String name;
	
	private SelectionShape(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
