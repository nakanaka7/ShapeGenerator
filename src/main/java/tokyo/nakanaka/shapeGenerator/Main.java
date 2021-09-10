package tokyo.nakanaka.shapeGenerator;

import java.util.List;

import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerDataRepository;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.CuboidSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.DiamondSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LineSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.RegularPolygonSelectionShapeStrategy;
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
		SelectionHandler selHandler = new SelectionHandler();
		selHandler.register(SelectionShape.CUBOID, new CuboidSelectionShapeStrategy());
		selHandler.register(SelectionShape.DIAMOND, new DiamondSelectionShapeStrategy());
		selHandler.register(SelectionShape.SPHERE, new SphereSelectionShapeStrategy());
		selHandler.register(SelectionShape.TORUS, new TorusSelectionShapeStrategy());
		selHandler.register(SelectionShape.LINE, new LineSelectionShapeStrategy());
		selHandler.register(SelectionShape.TRIANGLE, new TriangleSelectionShapeStrategy());
		selHandler.register(SelectionShape.TETRAHEDRON, new TetrahedronSelectionShapeStrategy());
		selHandler.register(SelectionShape.REGULAR_POLYGON, new RegularPolygonSelectionShapeStrategy());
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
