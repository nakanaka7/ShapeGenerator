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
import tokyo.nakanaka.shapeGenerator.selectionShape.ConeSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.CuboidSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.CylinderSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.DiamondSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.HollowConeSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.HollowCylinderSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.HollowRegularPrismSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.HollowRegularPyramidSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.HollowSphereSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.HollowTorusSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.LineSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.RegularPrismSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.RegularPyramidSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.SphereSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.TetrahedronSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.TorusSelection;
import tokyo.nakanaka.shapeGenerator.selectionShape.TriangleSelection;
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

import java.util.*;

/**
 * Main class for the project. 
 */
@PublicAPI
public class Main {
	public static final String SG = "/sg";
	private CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);
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
		dataCreatorMap.put(SelectionShape.CUBOID, CuboidSelection::newSelectionData);
		properMapMap.put(SelectionShape.CUBOID, CuboidSelection.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.CUBOID, CuboidSelection::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.CUBOID, CuboidSelection::onRightClick);
		selBuilderMap.put(SelectionShape.CUBOID, CuboidSelection::buildSelection);
		//diamond
		dataCreatorMap.put(SelectionShape.DIAMOND, DiamondSelection::newSelectionData);
		properMapMap.put(SelectionShape.DIAMOND, DiamondSelection.selSubCommandHandlerMap());
		selBuilderMap.put(SelectionShape.DIAMOND, DiamondSelection::buildSelection);
		//sphere
		dataCreatorMap.put(SelectionShape.SPHERE, SphereSelection::newSelectionData);
		properMapMap.put(SelectionShape.SPHERE, SphereSelection.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.SPHERE, SphereSelection::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.SPHERE, SphereSelection::onRightClick);
		selBuilderMap.put(SelectionShape.SPHERE, SphereSelection::buildSelection);
		//cylinder
		dataCreatorMap.put(SelectionShape.CYLINDER, CylinderSelection::newSelectionData);
		properMapMap.put(SelectionShape.CYLINDER, CylinderSelection.selSubCommandHandlerMap());
		selBuilderMap.put(SelectionShape.CYLINDER, CylinderSelection::buildSelection);
		//cone
		dataCreatorMap.put(SelectionShape.CONE, ConeSelection::newSelectionData);
		properMapMap.put(SelectionShape.CONE, ConeSelection.selSubCommandHandlerMap());
		selBuilderMap.put(SelectionShape.CONE, ConeSelection::buildSelection);
		//torus
		dataCreatorMap.put(SelectionShape.TORUS, TorusSelection::newSelectionData);
		properMapMap.put(SelectionShape.TORUS, TorusSelection.selSubCommandHandlerMap());
		selBuilderMap.put(SelectionShape.TORUS, TorusSelection::buildSelection);
		//line
		dataCreatorMap.put(SelectionShape.LINE, LineSelection::newSelectionData);
		properMapMap.put(SelectionShape.LINE, LineSelection.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.LINE, LineSelection::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.LINE, LineSelection::onRightClick);
		selBuilderMap.put(SelectionShape.LINE, LineSelection::buildSelection);
		//triangle
		dataCreatorMap.put(SelectionShape.TRIANGLE, TriangleSelection::newSelectionData);
		properMapMap.put(SelectionShape.TRIANGLE, TriangleSelection.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.TRIANGLE, TriangleSelection::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.TRIANGLE, TriangleSelection::onRightClick);
		selBuilderMap.put(SelectionShape.TRIANGLE, TriangleSelection::buildSelection);
		//tetrahedron
		dataCreatorMap.put(SelectionShape.TETRAHEDRON, TetrahedronSelection::newSelectionData);
		properMapMap.put(SelectionShape.TETRAHEDRON, TetrahedronSelection.selSubCommandHandlerMap());
		leftClickHandlerMap.put(SelectionShape.TETRAHEDRON, TetrahedronSelection::onLeftClick);
		rightClickHandlerMap.put(SelectionShape.TETRAHEDRON, TetrahedronSelection::onRightClick);
		selBuilderMap.put(SelectionShape.TETRAHEDRON, TetrahedronSelection::buildSelection);
		//regular prism
		dataCreatorMap.put(SelectionShape.REGULAR_PRISM, RegularPrismSelection::newSelectionData);
		properMapMap.put(SelectionShape.REGULAR_PRISM, RegularPrismSelection.selSubCommandHandlerMap());
		selBuilderMap.put(SelectionShape.REGULAR_PRISM, RegularPrismSelection::buildSelection);
		//regular pyramid
		dataCreatorMap.put(SelectionShape.REGULAR_PYRAMID, RegularPyramidSelection::newSelectionData);
		properMapMap.put(SelectionShape.REGULAR_PYRAMID, RegularPyramidSelection.selSubCommandHandlerMap());
		selBuilderMap.put(SelectionShape.REGULAR_PYRAMID, RegularPyramidSelection::buildSelection);
		//hollow sphere
		dataCreatorMap.put(SelectionShape.HOLLOW_SPHERE, HollowSphereSelection::newSelectionData);
		properMapMap.put(SelectionShape.HOLLOW_SPHERE, HollowSphereSelection.selSubCommandHandlerMap());
		selBuilderMap.put(SelectionShape.HOLLOW_SPHERE, HollowSphereSelection::buildSelection);
		//hollow cylinder
		dataCreatorMap.put(SelectionShape.HOLLOW_CYLINDER, HollowCylinderSelection::newSelectionData);
		properMapMap.put(SelectionShape.HOLLOW_CYLINDER, HollowCylinderSelection.selSubCommandHandlerMap());
		selBuilderMap.put(SelectionShape.HOLLOW_CYLINDER, HollowCylinderSelection::buildSelection);
		//hollow cone
		dataCreatorMap.put(SelectionShape.HOLLOW_CONE, HollowConeSelection::newSelectionData);
		properMapMap.put(SelectionShape.HOLLOW_CONE, HollowConeSelection.selSubCommandHandlerMap());
		selBuilderMap.put(SelectionShape.HOLLOW_CONE, HollowConeSelection::buildSelection);
		//hollow torus
		dataCreatorMap.put(SelectionShape.HOLLOW_TORUS, HollowTorusSelection::newSelectionData);
		properMapMap.put(SelectionShape.HOLLOW_TORUS, HollowTorusSelection.selSubCommandHandlerMap());
		selBuilderMap.put(SelectionShape.HOLLOW_TORUS, HollowTorusSelection::buildSelection);
		//hollow regular prism
		dataCreatorMap.put(SelectionShape.HOLLOW_REGULAR_PRISM, HollowRegularPrismSelection::newSelectionData);
		properMapMap.put(SelectionShape.HOLLOW_REGULAR_PRISM, HollowRegularPrismSelection.selSubCommandHandlerMap());
		selBuilderMap.put(SelectionShape.HOLLOW_REGULAR_PRISM, HollowRegularPrismSelection::buildSelection);
		//hollow regular pyramid
		dataCreatorMap.put(SelectionShape.HOLLOW_REGULAR_PYRAMID, HollowRegularPyramidSelection::newSelectionData);
		properMapMap.put(SelectionShape.HOLLOW_REGULAR_PYRAMID, HollowRegularPyramidSelection.selSubCommandHandlerMap());
		selBuilderMap.put(SelectionShape.HOLLOW_REGULAR_PYRAMID, HollowRegularPyramidSelection::buildSelection);
		//
		List<SelectionShape> selShapeList = dataCreatorMap.keySet().stream().toList();
		this.playerDataRepository = new PlayerDataRepository();
		this.cmdExecutorMap.put(SgSublabel.HELP, new HelpCommandExecutor(this.cmdLogColor));
		this.tabCompleterMap.put(SgSublabel.HELP, new HelpTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.VERSION, new VersionCommandExecutor(this.cmdLogColor, new SemVer(1, 2, 0)));
		this.tabCompleterMap.put(SgSublabel.VERSION, new VersionTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.WAND, new WandCommandExecutor(this.cmdLogColor));
		this.tabCompleterMap.put(SgSublabel.WAND, new WandTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.SHAPE, new ShapeCommandExecutor(this.cmdLogColor, dataCreatorMap));
		this.tabCompleterMap.put(SgSublabel.SHAPE, new ShapeTabCompleter(selShapeList));
		this.cmdExecutorMap.put(SgSublabel.SEL, new SelCommandExecutor(this.cmdLogColor, dataCreatorMap, properMapMap));
		this.tabCompleterMap.put(SgSublabel.SEL, new SelTabCompleter(dataCreatorMap, properMapMap));
		this.cmdExecutorMap.put(SgSublabel.GENR, new GenrCommandExecutor(this.cmdLogColor, selBuilderMap));
		this.tabCompleterMap.put(SgSublabel.GENR, new GenrTabCompleter(blockIDListFactory));
		this.cmdExecutorMap.put(SgSublabel.PHY, new PhyCommandExecutor(this.cmdLogColor));
		this.tabCompleterMap.put(SgSublabel.PHY, new PhyTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.SHIFT, new ShiftCommandExecutor(this.cmdLogColor));
		this.tabCompleterMap.put(SgSublabel.SHIFT, new ShiftTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.SCALE, new ScaleCommandExecutor(this.cmdLogColor));
		this.tabCompleterMap.put(SgSublabel.SCALE, new ScaleTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.MIRROR, new MirrorCommandExecutor(this.cmdLogColor));
		this.tabCompleterMap.put(SgSublabel.MIRROR, new MirrorTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.ROT, new RotCommandExecutor(this.cmdLogColor));
		this.tabCompleterMap.put(SgSublabel.ROT, new RotTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.MAX, new MaxCommandExecutor(this.cmdLogColor));
		this.tabCompleterMap.put(SgSublabel.MAX, new MaxTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.MIN, new MinCommandExecutor(this.cmdLogColor));
		this.tabCompleterMap.put(SgSublabel.MIN, new MinTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.DEL, new DelCommandExecutor(this.cmdLogColor));
		this.tabCompleterMap.put(SgSublabel.DEL, new DelTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.UNDO, new UndoCommandExecutor(this.cmdLogColor));
		this.tabCompleterMap.put(SgSublabel.UNDO, new UndoTabCompleter());
		this.cmdExecutorMap.put(SgSublabel.REDO, new RedoCommandExecutor(this.cmdLogColor));
		this.tabCompleterMap.put(SgSublabel.REDO, new RedoTabCompleter());
	}

	/**
	 * Handles "/sg" command
	 * @param cmdSender a command sender
	 * @param args arguments of the command line
	 */
	public void onSgCommand(CommandSender cmdSender, String[] args) {
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
		sgSubCmdExecutor.onCommand(playerData, player, subArgs);
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
		//check the selection shape is acceptable
		if(!this.leftClickHandlerMap.containsKey(selShape)){
			String shape = selShape.toString().toLowerCase();
			shape = shape.substring(0, 1).toUpperCase() + shape.substring(1);
			player.print(cmdLogColor.error() + "Wand Operation Is Invalid in " + shape + " Selection Mode");
			return;
		}
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
		lines.forEach(player::print);
	}
		
}
