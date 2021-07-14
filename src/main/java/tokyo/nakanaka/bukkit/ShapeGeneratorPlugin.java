package tokyo.nakanaka.bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import tokyo.nakanaka.ClickBlockEventHandler;
import tokyo.nakanaka.CommandLine;
import tokyo.nakanaka.Scheduler;
import tokyo.nakanaka.commandHandler.DelCommandHandler;
import tokyo.nakanaka.commandHandler.GenrCommandHandler;
import tokyo.nakanaka.commandHandler.MaxXCommandHandler;
import tokyo.nakanaka.commandHandler.MaxYCommandHandler;
import tokyo.nakanaka.commandHandler.MaxZCommandHandler;
import tokyo.nakanaka.commandHandler.MinXCommandHandler;
import tokyo.nakanaka.commandHandler.MinYCommandHandler;
import tokyo.nakanaka.commandHandler.MinZCommandHandler;
import tokyo.nakanaka.commandHandler.MirrorCommandHandler;
import tokyo.nakanaka.commandHandler.SgHelpCommandHandler;
import tokyo.nakanaka.commandHandler.PhyCommandHandler;
import tokyo.nakanaka.commandHandler.RedoCommandHandler;
import tokyo.nakanaka.commandHandler.SgCommandHandler;
import tokyo.nakanaka.commandHandler.RotCommandHandler;
import tokyo.nakanaka.commandHandler.ScaleCommandHandler;
import tokyo.nakanaka.commandHandler.SelCommandHandler;
import tokyo.nakanaka.commandHandler.ShapeCommandHandler;
import tokyo.nakanaka.commandHandler.ShiftCommandHandler;
import tokyo.nakanaka.commandHandler.SgSubCommandHandlerRepository;
import tokyo.nakanaka.commandHandler.UndoCommandHandler;
import tokyo.nakanaka.commandHandler.WandCommandHandler;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.player.PlayerRepository;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.CuboidSelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.LineSelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.RegularPolygonSelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SphereSelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.TetrahedronSelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.TorusSelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.TriangleSelectionStrategy;

public class ShapeGeneratorPlugin extends JavaPlugin{
	private CommandLineBuilder cmdLineBuilder;
	private SgCommandHandler sgCmdHandler;
	
	@Override
	public void onEnable() {
		PlayerRepository playerRepo = new PlayerRepository();
		this.cmdLineBuilder = new CommandLineBuilder(this.getServer(), playerRepo);
		SgSubCommandHandlerRepository cmdRepo = new SgSubCommandHandlerRepository();
		this.sgCmdHandler = new SgCommandHandler(cmdRepo);
		Map<SelectionShape, SelectionStrategy> strategyMap = new HashMap<>();
		strategyMap.put(SelectionShape.CUBOID, new CuboidSelectionStrategy());
		strategyMap.put(SelectionShape.SPHERE, new SphereSelectionStrategy());
		strategyMap.put(SelectionShape.TORUS, new TorusSelectionStrategy());
		strategyMap.put(SelectionShape.LINE, new LineSelectionStrategy());
		strategyMap.put(SelectionShape.TRIANGLE, new TriangleSelectionStrategy());
		strategyMap.put(SelectionShape.TETRAHEDRON, new TetrahedronSelectionStrategy());
		strategyMap.put(SelectionShape.REGULAR_POLYGON, new RegularPolygonSelectionStrategy());
		cmdRepo.register(new SgHelpCommandHandler(cmdRepo));
		cmdRepo.register(new WandCommandHandler(strategyMap));
		cmdRepo.register(new ShapeCommandHandler(strategyMap));
		cmdRepo.register(new SelCommandHandler(strategyMap));
		cmdRepo.register(new GenrCommandHandler(new BukkitBlockArgument(), strategyMap));
		cmdRepo.register(new PhyCommandHandler());
		cmdRepo.register(new ShiftCommandHandler());
		cmdRepo.register(new ScaleCommandHandler());
		cmdRepo.register(new MirrorCommandHandler());
		cmdRepo.register(new RotCommandHandler());
		cmdRepo.register(new MaxXCommandHandler());
		cmdRepo.register(new MaxYCommandHandler());
		cmdRepo.register(new MaxZCommandHandler());
		cmdRepo.register(new MinXCommandHandler());
		cmdRepo.register(new MinYCommandHandler());
		cmdRepo.register(new MinZCommandHandler());
		cmdRepo.register(new DelCommandHandler());
		cmdRepo.register(new UndoCommandHandler());
		cmdRepo.register(new RedoCommandHandler());
		PluginManager plManager = getServer().getPluginManager();
		ClickBlockEventHandler clickHandler = new ClickBlockEventHandler(strategyMap);
		Scheduler scheduler = new BukkitScheduler(this);
		plManager.registerEvents(new ClickBlockEventHandlerAdapter(this.getServer(), playerRepo, scheduler, clickHandler), this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandLine cmdLine = this.cmdLineBuilder.build(sender, label, args);
		Player player = cmdLine.getPlayer();
		this.sgCmdHandler.onCommand(player, args);
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
		CommandLine cmdLine = this.cmdLineBuilder.build(sender, alias, args);
		return this.sgCmdHandler.onTabComplete(cmdLine.getPlayer(), args);
	}
	
}
