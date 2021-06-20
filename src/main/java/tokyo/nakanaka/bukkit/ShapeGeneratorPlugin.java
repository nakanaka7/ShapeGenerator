package tokyo.nakanaka.bukkit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import tokyo.nakanaka.commandHandler.CommandHandlerRepository;
import tokyo.nakanaka.commandHandler.GenerateCommandHandler;
import tokyo.nakanaka.commandHandler.HelpCommandHandler;
import tokyo.nakanaka.commandHandler.PhysicsCommandHandler;
import tokyo.nakanaka.commandHandler.RedoCommandHandler;
import tokyo.nakanaka.commandHandler.RootCommandHandler;
import tokyo.nakanaka.commandHandler.SelCommandHandler;
import tokyo.nakanaka.commandHandler.SelShapeCommandHandler;
import tokyo.nakanaka.commandHandler.ShiftCommandHandler;
import tokyo.nakanaka.commandHandler.UndoCommandHandler;
import tokyo.nakanaka.commandLine.CommandLine;
import tokyo.nakanaka.selection.CuboidSelectionBuilder;
import tokyo.nakanaka.selection.SelectionManager;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.SphereSelectionBuilder;

public class ShapeGeneratorPlugin extends JavaPlugin{
	private CommandLineBuilder cmdLineBuilder;
	private CommandHandlerRepository cmdRepo;
	private RootCommandHandler rootCmdHandler;
	
	@Override
	public void onEnable() {
		this.cmdLineBuilder = new CommandLineBuilder(this.getServer());
		this.cmdRepo = new CommandHandlerRepository();
		this.rootCmdHandler = new RootCommandHandler(this.cmdRepo);
		SelectionManager selManager = new SelectionManager();
		selManager.register(SelectionShape.CUBOID, CuboidSelectionBuilder.class);
		selManager.register(SelectionShape.SPHERE, SphereSelectionBuilder.class);
		cmdRepo.register(new HelpCommandHandler(this.cmdRepo));
		cmdRepo.register(new PhysicsCommandHandler());
		cmdRepo.register(new SelCommandHandler(selManager));
		cmdRepo.register(new SelShapeCommandHandler(selManager));
		cmdRepo.register(new GenerateCommandHandler(new BukkitBlockArgument()));
		cmdRepo.register(new ShiftCommandHandler());
		cmdRepo.register(new UndoCommandHandler());
		cmdRepo.register(new RedoCommandHandler());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandLine cmdLine = this.cmdLineBuilder.build(sender, label, args);
		this.rootCmdHandler.onCommand(cmdLine);
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
		if(args.length == 1) {
			return new ArrayList<>(this.cmdRepo.getAliases());
		}
		CommandLine cmdLine = this.cmdLineBuilder.build(sender, alias, args);
		return this.rootCmdHandler.onTabComplete(cmdLine);
	}
	
}
