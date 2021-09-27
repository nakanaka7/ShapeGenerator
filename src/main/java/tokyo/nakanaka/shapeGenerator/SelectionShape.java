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
	REGULAR_PRISM("regular_prism"),
	REGULAR_PYRAMID("regular_pyramid"),
	HOLLOW_SPHERE("hollow_sphere"),
	HOLLOW_CYLINDER("hollow_cylinder"),
	HOLLOW_CONE("hollow_cone"),
	HOLLOW_TORUS("hollow_torus"),
	HOLLOW_REGULAR_PRISM("hollow_regular_prism"),
	HOLLOW_REGULAR_PYRAMID("hollow_regular_pyramid");

	private String name;
	
	SelectionShape(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
