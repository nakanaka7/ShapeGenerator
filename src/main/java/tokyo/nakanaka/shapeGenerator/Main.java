package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.*;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerDataRepository;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.cone.ConeSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.cuboid.CuboidSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.cylinder.CylinderSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.diamond.DiamondSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.hollowCone.HollowConeSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.hollowCylinder.HollowCylinderSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.hollowRegularPrism.HollowRegularPrismSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.hollowRegularPyramid.HollowRegularPyramidSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.hollowSphere.HollowSphereSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.hollowTorus.HollowTorusSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.line.LineSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.regularPrism.RegularPrismSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.regularPyramid.RegularPyramidSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.sphere.SphereSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.tetrahedron.TetrahedronSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.torus.TorusSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.triangle.TriangleSelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.GenrCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.SelCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.del.DelCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.del.DelTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.help.HelpCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.help.HelpTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.max.MaxCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.max.MaxTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.min.MinCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.min.MinTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.mirror.MirrorCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.mirror.MirrorTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.phy.PhyCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.phy.PhyTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.redo.RedoCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.redo.RedoTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.rot.RotCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.rot.RotTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.scale.ScaleCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.scale.ScaleTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.shape.ShapeCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.shape.ShapeTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.shift.ShiftCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.shift.ShiftTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.undo.UndoCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.undo.UndoTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.version.VersionCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.version.VersionTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.wand.WandCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.wand.WandTabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class for the project. 
 */
@PublicAPI
public class Main {
	public static final String SG = "/sg";
	private PlayerDataRepository playerDataRepository;
	private Map<SelectionShape, InitialSelectionDataCreator> dataCreatorMap = new HashMap<>();
	private SelectionShapeStrategyRepository shapeStrtgRepo;
	private Map<String, SgSubcommandExecutor> cmdExecutorMap = new HashMap<>();
	private Map<String, SgSubcommandTabCompleter> tabCompleterMap = new HashMap<>();

	public Main(BlockIDListFactory blockIDListFactory) {
		List<SelectionShape> shapeList = new ArrayList<>();
		SelectionShapeStrategyRepository shapeStrtgRepo = new SelectionShapeStrategyRepository();
		this.shapeStrtgRepo = shapeStrtgRepo;
		shapeList.add(SelectionShape.CUBOID);
		var cuboidStrtg = new CuboidSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.CUBOID, cuboidStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.CUBOID, cuboidStrtg);
		shapeList.add(SelectionShape.DIAMOND);
		var diamondStrtg = new DiamondSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.DIAMOND, diamondStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.DIAMOND, diamondStrtg);
		shapeList.add(SelectionShape.SPHERE);
		var sphereStrtg = new SphereSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.SPHERE, sphereStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.SPHERE, sphereStrtg);
		shapeList.add(SelectionShape.CYLINDER);
		var cylinderStrtg = new CylinderSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.CYLINDER, cylinderStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.CYLINDER, cylinderStrtg);
		shapeList.add(SelectionShape.CONE);
		var coneStrtg = new ConeSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.CONE, coneStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.CONE, coneStrtg);
		shapeList.add(SelectionShape.TORUS);
		var torusStrtg = new TorusSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.TORUS, torusStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.TORUS, torusStrtg);
		shapeList.add(SelectionShape.LINE);
		var lineStrtg = new LineSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.LINE, lineStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.LINE, lineStrtg);
		shapeList.add(SelectionShape.TRIANGLE);
		var triangleStrtg = new TriangleSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.TRIANGLE, triangleStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.TRIANGLE, triangleStrtg);
		shapeList.add(SelectionShape.TETRAHEDRON);
		var tetraStrtg = new TetrahedronSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.TETRAHEDRON, tetraStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.TETRAHEDRON, tetraStrtg);
		shapeList.add(SelectionShape.REGULAR_PRISM);
		var regularPrismStrtg = new RegularPrismSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.REGULAR_PRISM, regularPrismStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.REGULAR_PRISM, regularPrismStrtg);
		shapeList.add(SelectionShape.REGULAR_PYRAMID);
		var regularPyramidStrtg = new RegularPyramidSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.REGULAR_PYRAMID, regularPyramidStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.REGULAR_PYRAMID, regularPyramidStrtg);
		shapeList.add(SelectionShape.HOLLOW_SPHERE);
		var hollowSphereStrtg = new HollowSphereSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.HOLLOW_SPHERE, hollowSphereStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.HOLLOW_SPHERE, hollowSphereStrtg);
		shapeList.add(SelectionShape.HOLLOW_CYLINDER);
		var hollowCylinderStrtg = new HollowCylinderSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.HOLLOW_CYLINDER, hollowCylinderStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.HOLLOW_CYLINDER, hollowCylinderStrtg);
		shapeList.add(SelectionShape.HOLLOW_CONE);
		var hollowConeStrtg = new HollowConeSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.HOLLOW_CONE, hollowConeStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.HOLLOW_CONE, hollowConeStrtg);
		shapeList.add(SelectionShape.HOLLOW_TORUS);
		var hollowTorusStrtg = new HollowTorusSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.HOLLOW_TORUS, hollowTorusStrtg::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.HOLLOW_TORUS, hollowTorusStrtg);
		shapeList.add(SelectionShape.HOLLOW_REGULAR_PRISM);
		var hollowRegularPrism = new HollowRegularPrismSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.HOLLOW_REGULAR_PRISM, hollowRegularPrism::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.HOLLOW_REGULAR_PRISM, hollowRegularPrism);
		shapeList.add(SelectionShape.HOLLOW_REGULAR_PYRAMID);
		var hollowRegularPyramid = new HollowRegularPyramidSelectionShapeStrategy();
		dataCreatorMap.put(SelectionShape.HOLLOW_REGULAR_PYRAMID, hollowRegularPyramid::newSelectionData);
		shapeStrtgRepo.register(SelectionShape.HOLLOW_REGULAR_PYRAMID, hollowRegularPyramid);
		this.playerDataRepository = new PlayerDataRepository(shapeStrtgRepo);
		this.cmdExecutorMap.put(SgSublabel.HELP, new HelpCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.HELP, new HelpTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.VERSION, new VersionCommandExecutor(new SemVer(1, 2, 0)));
		this.tabCompleterMap.put(SgSublabel.VERSION, new VersionTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.WAND, new WandCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.WAND, new WandTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.SHAPE, new ShapeCommandExecutor(dataCreatorMap));
		this.tabCompleterMap.put(SgSublabel.SHAPE, new ShapeTabCompleter(shapeList));
		var selCmdHandler = new SelCommandHandler(shapeStrtgRepo);
		this.cmdExecutorMap.put(SgSublabel.SEL, selCmdHandler::onCommand);
		this.tabCompleterMap.put(SgSublabel.SEL, selCmdHandler::onTabComplete);
		var genrCmdHandler = new GenrCommandHandler(shapeStrtgRepo, blockIDListFactory);
		this.cmdExecutorMap.put(SgSublabel.GENR, genrCmdHandler::onCommand);
		this.tabCompleterMap.put(SgSublabel.GENR, genrCmdHandler::onTabComplete);
		this.cmdExecutorMap.put(SgSublabel.PHY, new PhyCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.PHY, new PhyTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.SHIFT, new ShiftCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.SHIFT, new ShiftTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.SCALE, new ScaleCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.SCALE, new ScaleTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.MIRROR, new MirrorCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.MIRROR, new MirrorTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.ROT, new RotCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.ROT, new RotTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.MAX, new MaxCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.MAX, new MaxTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.MIN, new MinCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.MIN, new MinTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.DEL, new DelCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.DEL, new DelTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.UNDO, new UndoCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.UNDO, new UndoTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.REDO, new RedoCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.REDO, new RedoTabCompleter());
	}

	/**
	 * Handles "/sg" command
	 * @param cmdSender a command sender
	 * @param args arguments of the command line
	 */
	public void onSgCommand(CommandSender cmdSender, String[] args) {
		CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);
		if(!(cmdSender instanceof Player player)) {
			cmdSender.print(cmdLogColor.error() + "Player only command");
			return;
		}
		if(args.length == 0) {
			cmdSender.print(cmdLogColor.error() + "Usage: /sg <subcommand>");
			cmdSender.print(cmdLogColor.error() + "Run \"/sg help [subcommand]\" for help");
			return;
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		SgSubcommandExecutor sgSubCmdExecutor = this.cmdExecutorMap.get(subLabel);
		if(sgSubCmdExecutor == null) {
			cmdSender.print(cmdLogColor.error() + "Unknown subcommand");
			cmdSender.print(cmdLogColor.error() + "Run \"/sg help [subcommand]\" for help");
			return;
		}
		PlayerData playerData = this.playerDataRepository.preparePlayerData(player);
		sgSubCmdExecutor.onCommand(playerData, player, subArgs, cmdLogColor);
	}

	/**
	 * Get a list for tab complete of "/sg" command
	 * @param cmdSender a command sender
	 * @param args arguments of the command line
	 * @return a list for tab complete of "/sg" command
	 */
	public List<String> onSgTabComplete(CommandSender cmdSender, String[] args) {
		if(!(cmdSender instanceof Player player)) {
			return List.of();
		}
		if(args.length == 1) {
			return 	new ArrayList<>(this.tabCompleterMap.keySet());
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		SgSubcommandTabCompleter tabCompleter = this.tabCompleterMap.get(subLabel);
		if(tabCompleter != null) {
			PlayerData playerData = this.playerDataRepository.preparePlayerData(player);
			return tabCompleter.onTabComplete(playerData, player, subArgs);
		}
		return List.of();
	}
	
	/**
	 * Handles a click block event
	 * @param evt a click block event
	 */
	public void onClickBlockEvent(ClickBlockEvent evt) {
		//ignore the event if the clicking item is not "minecraft:blaze_rod"
		Item item = evt.getItemStack().getItem();
		if(!item.equals(new Item(new NamespacedID("minecraft", "blaze_rod")))) {
			return;
		}
		evt.cancel();
		Player player = evt.getPlayer();
		BlockPosition blockPos = evt.getBlockPos();
		PlayerData playerData = this.playerDataRepository.preparePlayerData(player);
		SelectionShape selShape = playerData.getSelectionShape();
		SelectionShapeStrategy shapeStrtg = this.shapeStrtgRepo.get(selShape);
		//reset the selection builder if the world changes
		World evtWorld = blockPos.world();
		if(!evtWorld.equals(playerData.getSelectionData().world())) {
			SelectionData newSelData = this.dataCreatorMap.get(selShape).newSelectionData(evtWorld);
			playerData.setSelectionData(newSelData);
		}
		//update the selection builder
		SelectionData selData = playerData.getSelectionData();
		BlockVector3D v = new BlockVector3D(blockPos.x(), blockPos.y(), blockPos.z());
		try{
			switch(evt.getHandType()) {
				case LEFT_HAND -> shapeStrtg.onLeftClick(selData, v);
				case RIGHT_HAND -> shapeStrtg.onRightClick(selData, v);
			}
		}catch(IllegalStateException e) {
			player.print(LogColor.RED + "Invalid Operation");
			return;
		}
		//print the selection message
		List<String> lines = MessageUtils.selectionMessage(LogColor.GOLD, selShape, selData);
		lines.stream().forEach(player::print);
	}
		
}
