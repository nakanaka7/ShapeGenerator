package tokyo.nakanaka.shapeGenerator;

public enum SelectionShape {
	CUBOID("cuboid"),
	DIAMOND("diamond"),
	SPHERE("sphere"),
	TORUS("torus"),
	LINE("line"),
	TRIANGLE("triangle"),
	TETRAHEDRON("tetrahedron"),
	REGULAR_POLYGON("regular_polygon");

	private String name;
	
	private SelectionShape(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
