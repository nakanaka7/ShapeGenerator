package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.SemVer;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerDataRepository;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.*;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.*;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SelHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgBranchHelpConstants;

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
		this.sgCmdHandler.registerCommand(helpCmdHandler);
		helpCmdHandler.registerHelp(SgBranchHelpConstants.HELP);
		this.sgCmdHandler.registerCommand(new VersionCommandHandler(new SemVer(1, 2, 0)));
		helpCmdHandler.registerHelp(SgBranchHelpConstants.VERSION);
		this.sgCmdHandler.registerCommand(new WandCommandHandler());
		this.sgCmdHandler.registerCommand(new ShapeCommandHandler(shapeStrtgRepo));
		helpCmdHandler.registerHelp(SgBranchHelpConstants.SHAPE);
		this.sgCmdHandler.registerCommand(new SelCommandHandler(shapeStrtgRepo));
		helpCmdHandler.registerHelp(new SelHelp());
		this.sgCmdHandler.registerCommand(new GenrCommandHandler(shapeStrtgRepo, blockIDListFactory));
		helpCmdHandler.registerHelp(SgBranchHelpConstants.GENR);
		this.sgCmdHandler.registerCommand(new PhyCommandHandler());
		helpCmdHandler.registerHelp(SgBranchHelpConstants.PHY);
		this.sgCmdHandler.registerCommand(new ShiftCommandHandler());
		helpCmdHandler.registerHelp(SgBranchHelpConstants.SHIFT);
		this.sgCmdHandler.registerCommand(new ScaleCommandHandler());
		helpCmdHandler.registerHelp(SgBranchHelpConstants.SCALE);
		this.sgCmdHandler.registerCommand(new MirrorCommandHandler());
		helpCmdHandler.registerHelp(SgBranchHelpConstants.MIRROR);
		this.sgCmdHandler.registerCommand(new RotCommandHandler());
		helpCmdHandler.registerHelp(SgBranchHelpConstants.ROT);
		this.sgCmdHandler.registerCommand(new MaxCommandHandler());
		helpCmdHandler.registerHelp(SgBranchHelpConstants.MAX);
		this.sgCmdHandler.registerCommand(new MinCommandHandler());
		helpCmdHandler.registerHelp(SgBranchHelpConstants.MIN);
		this.sgCmdHandler.registerCommand(new DelCommandHandler());
		helpCmdHandler.registerHelp(SgBranchHelpConstants.DEL);
		this.sgCmdHandler.registerCommand(new UndoCommandHandler());
		helpCmdHandler.registerHelp(SgBranchHelpConstants.UNDO);
		this.sgCmdHandler.registerCommand(new RedoCommandHandler());
		helpCmdHandler.registerHelp(SgBranchHelpConstants.REDO);
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
