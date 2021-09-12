package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.annotation.PublicAPI;

@PublicAPI
public enum SelectionShape {
	CUBOID("cuboid"),
	DIAMOND("diamond"),
	SPHERE("sphere"),
	CONE("cone"),
	TORUS("torus"),
	LINE("line"),
	TRIANGLE("triangle"),
	TETRAHEDRON("tetrahedron"),
	REGULAR_POLYGON("regular_polygon"),
	HOLLOW_SPHERE("hollow_sphere");

	private String name;
	
	private SelectionShape(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
