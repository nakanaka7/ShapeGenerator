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
import tokyo.nakanaka.shapeGenerator.sgSubcommand.del.DelCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.del.DelTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.genr.GenrCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.genr.GenrTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.help.HelpCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.help.HelpTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.max.MaxCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.max.MaxTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.min.MinCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.min.MinTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.mirror.MirrorCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.mirror.MirrorTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.phy.PhyCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.phy.PhyTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.redo.RedoCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.redo.RedoTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.rot.RotCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.rot.RotTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.scale.ScaleCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.scale.ScaleTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.sel.SelCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.sel.SelTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.shape.ShapeCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.shape.ShapeTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.shift.ShiftCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.shift.ShiftTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.undo.UndoCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.undo.UndoTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.version.VersionCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.version.VersionTabCompleter;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.wand.WandCommandExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.wand.WandTabCompleter;

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
	private Map<SelectionShape, LeftClickHandler> leftClickHandlerMap = new HashMap<>();
	private Map<SelectionShape, RightClickHandler> rightClickHandlerMap = new HashMap<>();
 	private Map<String, SgSubcommandExecutor> cmdExecutorMap = new HashMap<>();
	private Map<String, SgSubcommandTabCompleter> tabCompleterMap = new HashMap<>();

	public Main(BlockIDListFactory blockIDListFactory) {
		Map<SelectionShape, Map<String, SubCommandHandler>> properMapMap = new HashMap<>();
		Map<SelectionShape, SelectionBuilder> selBuilderMap = new HashMap<>();
		//cuboid
		dataCreatorMap.put(SelectionShape.CUBOID, CuboidSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.CUBOID, CuboidSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.CUBOID, CuboidSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.CUBOID, CuboidSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.CUBOID, CuboidSelectionShapeStrategy::buildSelection);
		//diamond
		dataCreatorMap.put(SelectionShape.DIAMOND, DiamondSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.DIAMOND, DiamondSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.DIAMOND, DiamondSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.DIAMOND, DiamondSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.DIAMOND, DiamondSelectionShapeStrategy::buildSelection);
		//sphere
		dataCreatorMap.put(SelectionShape.SPHERE, SphereSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.SPHERE, SphereSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.SPHERE, SphereSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.SPHERE, SphereSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.SPHERE, SphereSelectionShapeStrategy::buildSelection);
		//cylinder
		dataCreatorMap.put(SelectionShape.CYLINDER, CylinderSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.CYLINDER, CylinderSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.CYLINDER, CylinderSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.CYLINDER, CylinderSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.CYLINDER, CylinderSelectionShapeStrategy::buildSelection);
		//cone
		dataCreatorMap.put(SelectionShape.CONE, ConeSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.CONE, ConeSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.CONE, ConeSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.CONE, ConeSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.CONE, ConeSelectionShapeStrategy::buildSelection);
		//torus
		dataCreatorMap.put(SelectionShape.TORUS, TorusSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.TORUS, TorusSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.TORUS, TorusSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.TORUS, TorusSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.TORUS, TorusSelectionShapeStrategy::buildSelection);
		//line
		dataCreatorMap.put(SelectionShape.LINE, LineSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.LINE, LineSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.LINE, LineSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.LINE, LineSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.LINE, LineSelectionShapeStrategy::buildSelection);
		//triangle
		dataCreatorMap.put(SelectionShape.TRIANGLE, TriangleSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.TRIANGLE, TriangleSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.TRIANGLE, TriangleSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.TRIANGLE, TriangleSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.TRIANGLE, TriangleSelectionShapeStrategy::buildSelection);
		//tetrahedron
		dataCreatorMap.put(SelectionShape.TETRAHEDRON, TetrahedronSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.TETRAHEDRON, TetrahedronSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.TETRAHEDRON, TetrahedronSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.TETRAHEDRON, TetrahedronSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.TETRAHEDRON, TetrahedronSelectionShapeStrategy::buildSelection);
		//regular prism
		dataCreatorMap.put(SelectionShape.REGULAR_PRISM, RegularPrismSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.REGULAR_PRISM, RegularPrismSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.REGULAR_PRISM, RegularPrismSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.REGULAR_PRISM, RegularPrismSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.REGULAR_PRISM, RegularPrismSelectionShapeStrategy::buildSelection);
		//regular pyramid
		dataCreatorMap.put(SelectionShape.REGULAR_PYRAMID, RegularPyramidSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.REGULAR_PYRAMID, RegularPyramidSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.REGULAR_PYRAMID, RegularPyramidSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.REGULAR_PYRAMID, RegularPyramidSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.REGULAR_PYRAMID, RegularPyramidSelectionShapeStrategy::buildSelection);
		//hollow sphere
		dataCreatorMap.put(SelectionShape.HOLLOW_SPHERE, HollowSphereSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.HOLLOW_SPHERE, HollowSphereSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.HOLLOW_SPHERE, HollowSphereSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.HOLLOW_SPHERE, HollowSphereSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.HOLLOW_SPHERE, HollowSphereSelectionShapeStrategy::buildSelection);
		//hollow cylinder
		dataCreatorMap.put(SelectionShape.HOLLOW_CYLINDER, HollowCylinderSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.HOLLOW_CYLINDER, HollowCylinderSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.HOLLOW_CYLINDER, HollowCylinderSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.HOLLOW_CYLINDER, HollowCylinderSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.HOLLOW_CYLINDER, HollowCylinderSelectionShapeStrategy::buildSelection);
		//hollow cone
		dataCreatorMap.put(SelectionShape.HOLLOW_CONE, HollowConeSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.HOLLOW_CONE, HollowConeSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.HOLLOW_CONE, HollowConeSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.HOLLOW_CONE, HollowConeSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.HOLLOW_CONE, HollowConeSelectionShapeStrategy::buildSelection);
		//hollow torus
		dataCreatorMap.put(SelectionShape.HOLLOW_TORUS, HollowTorusSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.HOLLOW_TORUS, HollowTorusSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.HOLLOW_TORUS, HollowTorusSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.HOLLOW_TORUS, HollowTorusSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.HOLLOW_TORUS, HollowTorusSelectionShapeStrategy::buildSelection);
		//hollow regular prism
		dataCreatorMap.put(SelectionShape.HOLLOW_REGULAR_PRISM, HollowRegularPrismSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.HOLLOW_REGULAR_PRISM, HollowRegularPrismSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.HOLLOW_REGULAR_PRISM, HollowRegularPrismSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.HOLLOW_REGULAR_PRISM, HollowRegularPrismSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.HOLLOW_REGULAR_PRISM, HollowRegularPrismSelectionShapeStrategy::buildSelection);
		//hollow regular pyramid
		dataCreatorMap.put(SelectionShape.HOLLOW_REGULAR_PYRAMID, HollowRegularPyramidSelectionShapeStrategy::newSelectionData);
		properMapMap.put(SelectionShape.HOLLOW_REGULAR_PYRAMID, HollowRegularPyramidSelectionShapeStrategy.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.HOLLOW_REGULAR_PYRAMID, HollowRegularPyramidSelectionShapeStrategy::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.HOLLOW_REGULAR_PYRAMID, HollowRegularPyramidSelectionShapeStrategy::onRightClick);
		selBuilderMap.put(SelectionShape.HOLLOW_REGULAR_PYRAMID, HollowRegularPyramidSelectionShapeStrategy::buildSelection);
		//
		List<SelectionShape> selShapeList = dataCreatorMap.keySet().stream().toList();
		this.playerDataRepository = new PlayerDataRepository();
		this.cmdExecutorMap.put(SgSublabel.HELP, new HelpCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.HELP, new HelpTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.VERSION, new VersionCommandExecutor(new SemVer(1, 2, 0)));
		this.tabCompleterMap.put(SgSublabel.VERSION, new VersionTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.WAND, new WandCommandExecutor());
		this.tabCompleterMap.put(SgSublabel.WAND, new WandTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.SHAPE, new ShapeCommandExecutor(dataCreatorMap));
		this.tabCompleterMap.put(SgSublabel.SHAPE, new ShapeTabCompleter(selShapeList));
		this.cmdExecutorMap.put(SgSublabel.SEL, new SelCommandExecutor(dataCreatorMap, properMapMap));
		this.tabCompleterMap.put(SgSublabel.SEL, new SelTabCompleter(dataCreatorMap, properMapMap));
		this.cmdExecutorMap.put(SgSublabel.GENR, new GenrCommandExecutor(selBuilderMap));
		this.tabCompleterMap.put(SgSublabel.GENR, new GenrTabCompleter(blockIDListFactory));
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
				case LEFT_HAND -> this.leftClickHandlerMap.get(selShape).onLeftClick(selData, v);
				case RIGHT_HAND -> this.rightClickHandlerMap.get(selShape).onRightClick(selData, v);
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
