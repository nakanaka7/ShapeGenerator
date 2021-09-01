package tokyo.nakanaka.shapeGenerator.selectionStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.shapeGenerator.SelectionShapeNew;

@Deprecated
public class SelectionStrategySource {
	private Map<SelectionShapeNew, SelectionStrategy> strategyMap = new HashMap<>();
	
	public SelectionStrategySource() {
		strategyMap.put(SelectionShapeNew.CUBOID, new CuboidSelectionStrategy());
		strategyMap.put(SelectionShapeNew.DIAMOND, new DiamondSelectionStrategy());
		strategyMap.put(SelectionShapeNew.SPHERE, new SphereSelectionStrategy());
		strategyMap.put(SelectionShapeNew.TORUS, new TorusSelectionStrategy());
		strategyMap.put(SelectionShapeNew.LINE, new LineSelectionStrategy());
		strategyMap.put(SelectionShapeNew.TRIANGLE, new TriangleSelectionStrategy());
		strategyMap.put(SelectionShapeNew.TETRAHEDRON, new TetrahedronSelectionStrategy());
		strategyMap.put(SelectionShapeNew.REGULAR_POLYGON, new RegularPolygonSelectionStrategy());
	}
	
	public SelectionStrategy get(SelectionShapeNew shape) {
		return this.strategyMap.get(shape);
	}
	
	public List<SelectionShapeNew> getShapeList() {
		return new ArrayList<>(this.strategyMap.keySet());
	}
	
	public SelectionShapeNew getDefaultSelectionShape() {
		return SelectionShapeNew.CUBOID;
	}
	
}
