package tokyo.nakanaka.selection.selectionStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.selection.SelectionShape;

public class SelectionStrategySource {
	private Map<SelectionShape, SelectionStrategy> strategyMap = new HashMap<>();
	
	public SelectionStrategySource() {
		strategyMap.put(SelectionShape.CUBOID, new CuboidSelectionStrategy());
		strategyMap.put(SelectionShape.DIAMOND, new DiamondSelectionStrategy());
		strategyMap.put(SelectionShape.SPHERE, new SphereSelectionStrategy());
		strategyMap.put(SelectionShape.TORUS, new TorusSelectionStrategy());
		strategyMap.put(SelectionShape.LINE, new LineSelectionStrategy());
		strategyMap.put(SelectionShape.TRIANGLE, new TriangleSelectionStrategy());
		strategyMap.put(SelectionShape.TETRAHEDRON, new TetrahedronSelectionStrategy());
		strategyMap.put(SelectionShape.REGULAR_POLYGON, new RegularPolygonSelectionStrategy());
	}
	
	public SelectionStrategy get(SelectionShape shape) {
		return this.strategyMap.get(shape);
	}
	
	public List<SelectionShape> getShapeList() {
		return new ArrayList<>(this.strategyMap.keySet());
	}
	
	public SelectionShape getDefaultSelectionShape() {
		return SelectionShape.CUBOID;
	}
	
}
