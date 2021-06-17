package tokyo.nakanaka.bukkit;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import tokyo.nakanaka.commandHandler.GenerateCommandHandler;
import tokyo.nakanaka.commandHandler.RootCommandHandler;
import tokyo.nakanaka.commandHandler.SelCommandHandler;
import tokyo.nakanaka.commandHandler.SelShapeCommandHandler;
import tokyo.nakanaka.commandLine.CommandLine;

public class ShapeGeneratorPlugin extends JavaPlugin{
	private CommandLineBuilder cmdLineBuilder;
	private RootCommandHandler rootCmdHandler;
	
	@Override
	public void onEnable() {
		this.cmdLineBuilder = new CommandLineBuilder(this.getServer());
		this.rootCmdHandler = new RootCommandHandler();
		this.rootCmdHandler.register(new SelCommandHandler());
		this.rootCmdHandler.register(new SelShapeCommandHandler());
		this.rootCmdHandler.register(new GenerateCommandHandler());
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
			return this.rootCmdHandler.getAliases();
		}
		CommandLine cmdLine = this.cmdLineBuilder.build(sender, alias, args);
		return this.rootCmdHandler.onTabComplete(cmdLine);
	}
	
}
