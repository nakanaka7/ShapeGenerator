package tokyo.nakanaka.bukkit;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import tokyo.nakanaka.ClickBlockEventHandler;
import tokyo.nakanaka.CommandLine;
import tokyo.nakanaka.Scheduler;
import tokyo.nakanaka.commandHandler.CommandHandlerRepository;
import tokyo.nakanaka.commandHandler.DelCommandHandler;
import tokyo.nakanaka.commandHandler.GenrCommandHandler;
import tokyo.nakanaka.commandHandler.HelpCommandHandler;
import tokyo.nakanaka.commandHandler.PhyCommandHandler;
import tokyo.nakanaka.commandHandler.RedoCommandHandler;
import tokyo.nakanaka.commandHandler.SgCommandHandler;
import tokyo.nakanaka.commandHandler.RotCommandHandler;
import tokyo.nakanaka.commandHandler.ScaleCommandHandler;
import tokyo.nakanaka.commandHandler.SelCommandHandler;
import tokyo.nakanaka.commandHandler.ShapeCommandHandler;
import tokyo.nakanaka.commandHandler.ShiftCommandHandler;
import tokyo.nakanaka.commandHandler.UndoCommandHandler;
import tokyo.nakanaka.player.PlayerRepository;
import tokyo.nakanaka.selection.CuboidSelectionBuilder;
import tokyo.nakanaka.selection.SelectionManager;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.SphereSelectionBuilder;
import tokyo.nakanaka.selection.TorusSelectionBuilder;

public class ShapeGeneratorPlugin extends JavaPlugin{
	private CommandLineBuilder cmdLineBuilder;
	private SgCommandHandler rootCmdHandler;
	
	@Override
	public void onEnable() {
		PlayerRepository playerRepo = new PlayerRepository();
		this.cmdLineBuilder = new CommandLineBuilder(this.getServer(), playerRepo);
		CommandHandlerRepository cmdRepo = new CommandHandlerRepository();
		this.rootCmdHandler = new SgCommandHandler(cmdRepo);
		SelectionManager selManager = new SelectionManager();
		selManager.register(SelectionShape.CUBOID, CuboidSelectionBuilder.class);
		selManager.register(SelectionShape.SPHERE, SphereSelectionBuilder.class);
		selManager.register(SelectionShape.TORUS, TorusSelectionBuilder.class);
		cmdRepo.register(new HelpCommandHandler(cmdRepo));
		cmdRepo.register(new PhyCommandHandler());
		cmdRepo.register(new SelCommandHandler(selManager));
		cmdRepo.register(new ShapeCommandHandler(selManager));
		cmdRepo.register(new GenrCommandHandler(new BukkitBlockArgument()));
		cmdRepo.register(new ShiftCommandHandler());
		cmdRepo.register(new ScaleCommandHandler());
		cmdRepo.register(new RotCommandHandler());
		cmdRepo.register(new DelCommandHandler());
		cmdRepo.register(new UndoCommandHandler());
		cmdRepo.register(new RedoCommandHandler());
		PluginManager plManager = getServer().getPluginManager();
		ClickBlockEventHandler clickHandler = new ClickBlockEventHandler(selManager);
		Scheduler scheduler = new BukkitScheduler(this);
		plManager.registerEvents(new ClickBlockEventHandlerAdapter(this.getServer(), playerRepo, scheduler, clickHandler), this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandLine cmdLine = this.cmdLineBuilder.build(sender, label, args);
		this.rootCmdHandler.onCommand(cmdLine.getPlayer(), cmdLine.getAlias(), cmdLine.getArgs());
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
		CommandLine cmdLine = this.cmdLineBuilder.build(sender, alias, args);
		return this.rootCmdHandler.onTabComplete(cmdLine.getPlayer(), cmdLine.getAlias(), cmdLine.getArgs());
	}
	
}
