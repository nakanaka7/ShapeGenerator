package tokyo.nakanaka.shapeGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerDataRepository;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.CuboidSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.DiamondSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LineSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.RegularPolygonSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SphereSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.TetrahedronSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.TorusSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.TriangleSelectionShapeStrategy;

/**
 * Main class for the project. 
 */
public class Main {
	private SgCommandHandler sgCmdHandler;
	private SgEventHandler sgEvtHandler;
	
	public Main(BlockIDListFactory blockIDListFactory) {
		Map<SelectionShape, SelectionShapeStrategy> selStrtgMap = new HashMap<>();
		selStrtgMap.put(SelectionShape.CUBOID, new CuboidSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.DIAMOND, new DiamondSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.SPHERE, new SphereSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.TORUS, new TorusSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.LINE, new LineSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.TRIANGLE, new TriangleSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.TETRAHEDRON, new TetrahedronSelectionShapeStrategy());
		selStrtgMap.put(SelectionShape.REGULAR_POLYGON, new RegularPolygonSelectionShapeStrategy());
		var selHandler = new SelectionHandler(selStrtgMap);
		var playerDataRepository = new PlayerDataRepository(selHandler);
		this.sgCmdHandler = new SgCommandHandler(playerDataRepository, selHandler, blockIDListFactory);
		this.sgEvtHandler = new SgEventHandler(playerDataRepository, selHandler);
	}
	
	/**
	 * Handles "/sg" command
	 * @param cmdSender a command sender
	 * @param args arguments of the command line
	 */
	public void onSgCommand(CommandSender cmdSender, String[] args) {
		this.sgCmdHandler.onCommand(cmdSender, args);
	}
	
	/**
	 * Get a list for tab complete of "/sg" command
	 * @param cmdSender a command sender
	 * @param args arguments of the command line
	 * @return a list for tab complete of "/sg" command
	 */
	public List<String> onSgTabComplete(CommandSender cmdSender, String[] args) {
		return this.sgCmdHandler.onTabComplete(cmdSender, args);
	}
	
	/**
	 * Handles a click block event
	 * @param evt a click block event
	 */
	public void onClickBlockEvent(ClickBlockEvent evt) {
		this.sgEvtHandler.onClickBlockEvent(evt);
	}
		
}
