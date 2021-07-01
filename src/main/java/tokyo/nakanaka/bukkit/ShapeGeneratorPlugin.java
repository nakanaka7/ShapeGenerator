package tokyo.nakanaka.bukkit;

import static tokyo.nakanaka.logger.LogColor.GOLD;
import static tokyo.nakanaka.logger.LogColor.RESET;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import tokyo.nakanaka.ClickBlockEventHandler;
import tokyo.nakanaka.CommandLine;
import tokyo.nakanaka.Pair;
import tokyo.nakanaka.Scheduler;
import tokyo.nakanaka.commandHandler.DelCommandHandler;
import tokyo.nakanaka.commandHandler.GenrCommandHandler;
import tokyo.nakanaka.commandHandler.HelpCommandHandler;
import tokyo.nakanaka.commandHandler.PhyCommandHandler;
import tokyo.nakanaka.commandHandler.RedoCommandHandler;
import tokyo.nakanaka.commandHandler.RootCommandHandler;
import tokyo.nakanaka.commandHandler.RotCommandHandler;
import tokyo.nakanaka.commandHandler.ScaleCommandHandler;
import tokyo.nakanaka.commandHandler.SelCommandHandler;
import tokyo.nakanaka.commandHandler.ShapeCommandHandlerOld;
import tokyo.nakanaka.commandHandler.ShiftCommandHandler;
import tokyo.nakanaka.commandHandler.SubCommandHandlerRepository;
import tokyo.nakanaka.commandHandler.UndoCommandHandler;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.player.PlayerRepository;
import tokyo.nakanaka.selection.SelectionManager;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.SelectionStrategy;
import tokyo.nakanaka.selection.TorusSelectionBuilder;
import tokyo.nakanaka.selection.cuboid.CuboidSelectionBuilder;
import tokyo.nakanaka.selection.cuboid.CuboidSelectionStrategy;
import tokyo.nakanaka.selection.sphere.SphereSelectionBuilder;

public class ShapeGeneratorPlugin extends JavaPlugin{
	private CommandLineBuilder cmdLineBuilder;
	private RootCommandHandler sgCmdHandler;
	
	@Override
	public void onEnable() {
		PlayerRepository playerRepo = new PlayerRepository();
		this.cmdLineBuilder = new CommandLineBuilder(this.getServer(), playerRepo);
		SubCommandHandlerRepository cmdRepo = new SubCommandHandlerRepository();
		this.sgCmdHandler = new RootCommandHandler("sg" ,cmdRepo);
		SelectionManager selManager = new SelectionManager();
		selManager.register(SelectionShape.CUBOID, CuboidSelectionBuilder.class);
		selManager.register(SelectionShape.SPHERE, SphereSelectionBuilder.class);
		selManager.register(SelectionShape.TORUS, TorusSelectionBuilder.class);
		Map<SelectionShape, SelectionStrategy> strategyMap = new HashMap<>();
		strategyMap.put(SelectionShape.CUBOID, new CuboidSelectionStrategy());
		cmdRepo.register(new HelpCommandHandler(cmdRepo));
		cmdRepo.register(new PhyCommandHandler());
		cmdRepo.register(new SelCommandHandler(strategyMap));
		cmdRepo.register(new ShapeCommandHandlerOld(selManager));
		cmdRepo.register(new GenrCommandHandler(new BukkitBlockArgument(), strategyMap));
		cmdRepo.register(new ShiftCommandHandler());
		cmdRepo.register(new ScaleCommandHandler());
		cmdRepo.register(new RotCommandHandler());
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
		boolean success = this.sgCmdHandler.onCommand(player, args);
		if(!success) {
			List<Pair<String, String>> list = this.sgCmdHandler.getSubCommmandDescriptions();
			for(Pair<String, String> pair : list) {
				player.getLogger().print(GOLD + pair.getFirst() + ": " + RESET + pair.getSecond());
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
		CommandLine cmdLine = this.cmdLineBuilder.build(sender, alias, args);
		return this.sgCmdHandler.onTabComplete(cmdLine.getPlayer(), args);
	}
	
}
