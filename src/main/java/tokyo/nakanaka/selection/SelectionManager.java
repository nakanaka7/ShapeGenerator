package tokyo.nakanaka.selection;

public class SelectionManager {
	public SelectionBuilder getSelectionBuilder(SelectionShape shape) {
		switch(shape){
		case CUBOID:
			return new CuboidSelectionBuilder();
		default:
			return null;
		}
	}
	
	public SelectionShape getSelectionShape(SelectionBuilder builder) {
		if(builder instanceof CuboidSelectionBuilder) {
			return SelectionShape.CUBOID;
		}else {
			return null;
		}
	}
}
