package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.CuboidSelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.LineSelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.RegularPolygonSelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.selection.selectionStrategy.SphereSelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.TetrahedronSelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.TorusSelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.TriangleSelectionStrategy;

public class SgCommandHandlerNew {
	private List<CommandHandler> subList = new ArrayList<>();
	
	public SgCommandHandlerNew(BlockCommandArgument blockArg) {
		Map<SelectionShape, SelectionStrategy> strategyMap = new HashMap<>();
		strategyMap.put(SelectionShape.CUBOID, new CuboidSelectionStrategy());
		strategyMap.put(SelectionShape.SPHERE, new SphereSelectionStrategy());
		strategyMap.put(SelectionShape.TORUS, new TorusSelectionStrategy());
		strategyMap.put(SelectionShape.LINE, new LineSelectionStrategy());
		strategyMap.put(SelectionShape.TRIANGLE, new TriangleSelectionStrategy());
		strategyMap.put(SelectionShape.TETRAHEDRON, new TetrahedronSelectionStrategy());
		strategyMap.put(SelectionShape.REGULAR_POLYGON, new RegularPolygonSelectionStrategy());
		SelectionStrategySource selStraSource = new SelectionStrategySource();
		this.subList.add(new PhyCommandHandler());
		this.subList.add(new SelCommandHandler(selStraSource));
		this.subList.add(new ShapeCommandHandler(selStraSource));
		this.subList.add(new GenrCommandHandler(blockArg, selStraSource));
		this.subList.add(new ShiftCommandHandler());
		this.subList.add(new ScaleCommandHandler());
		this.subList.add(new RotCommandHandler());
		this.subList.add(new DelCommandHandler());
		this.subList.add(new UndoCommandHandler());
		this.subList.add(new RedoCommandHandler());
	}
	
	public String getLabel() {
		return "sg";
	}
	
	public List<CommandHandler> getSubList(Player player) {
		return this.subList;
	}
	
}
