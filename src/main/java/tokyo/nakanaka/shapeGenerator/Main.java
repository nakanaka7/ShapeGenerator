package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.SemVer;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerDataRepository;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.*;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.*;

import java.util.List;

/**
 * Main class for the project. 
 */
@PublicAPI
public class Main {
	private SgCommandHandler sgCmdHandler;
	private SgEventHandler sgEvtHandler;
	
	public Main(BlockIDListFactory blockIDListFactory) {
		SelectionShapeStrategyRepository shapeStrtgRepo = prepareStrategyRepository();
		var playerDataRepo = new PlayerDataRepository(shapeStrtgRepo);
		this.sgCmdHandler = new SgCommandHandler(playerDataRepo);
		this.registerCommands(shapeStrtgRepo, blockIDListFactory);
		this.sgEvtHandler = new SgEventHandler(playerDataRepo, shapeStrtgRepo);
	}

	private static SelectionShapeStrategyRepository prepareStrategyRepository(){
		SelectionShapeStrategyRepository shapeStrtgRepo = new SelectionShapeStrategyRepository();
		shapeStrtgRepo.register(SelectionShape.CUBOID, new CuboidSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.DIAMOND, new DiamondSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.SPHERE, new SphereSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.CYLINDER, new CylinderSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.CONE, new ConeSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.TORUS, new TorusSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.LINE, new LineSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.TRIANGLE, new TriangleSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.TETRAHEDRON, new TetrahedronSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.REGULAR_PRISM, new RegularPrismSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.REGULAR_PYRAMID, new RegularPyramidSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.HOLLOW_SPHERE, new HollowSphereSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.HOLLOW_CYLINDER, new HollowCylinderSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.HOLLOW_CONE, new HollowConeSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.HOLLOW_TORUS, new HollowTorusSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.HOLLOW_REGULAR_PRISM, new HollowRegularPrismSelectionShapeStrategy());
		shapeStrtgRepo.register(SelectionShape.HOLLOW_REGULAR_PYRAMID, new HollowRegularPyramidSelectionShapeStrategy());
		return shapeStrtgRepo;
	}

	private void registerCommands(SelectionShapeStrategyRepository shapeStrtgRepo, BlockIDListFactory blockIDListFactory){
		HelpCommandHandler helpCmdHandler = new HelpCommandHandler();
		this.registerBranchCommand(helpCmdHandler);
		this.registerBranchCommand(new VersionCommandHandler(new SemVer(1, 2, 0)));
		this.registerBranchCommand(new WandCommandHandler());
		this.registerBranchCommand(new ShapeCommandHandler(shapeStrtgRepo));
		this.sgCmdHandler.registerCommand(new SelCommandHandler(shapeStrtgRepo));
		this.registerBranchCommand(new GenrCommandHandler(shapeStrtgRepo, blockIDListFactory));
		this.registerBranchCommand(new PhyCommandHandler());
		this.registerBranchCommand(new ShiftCommandHandler());
		this.registerBranchCommand(new ScaleCommandHandler());
		this.registerBranchCommand(new MirrorCommandHandler());
		this.registerBranchCommand(new RotCommandHandler());
		this.registerBranchCommand(new MaxCommandHandler());
		this.registerBranchCommand(new MinCommandHandler());
		this.registerBranchCommand(new DelCommandHandler());
		this.registerBranchCommand(new UndoCommandHandler());
		this.registerBranchCommand(new RedoCommandHandler());
	}

	private void registerBranchCommand(BranchCommandHandler handler) {
		this.sgCmdHandler.registerCommand(handler);
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
