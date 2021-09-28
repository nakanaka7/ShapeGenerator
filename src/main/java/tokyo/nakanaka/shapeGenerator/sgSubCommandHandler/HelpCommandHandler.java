package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Handles "/sg help" command
 */
public class HelpCommandHandler implements SubCommandHandler {
	private static final CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);
	private LinkedHashMap<String, CommandHelp> cmdHelpMap = new LinkedHashMap<>();
	
	public HelpCommandHandler() {
		this.cmdHelpMap.put("version", new VersionHelp());
		this.cmdHelpMap.put("help", new HelpHelp());
		this.cmdHelpMap.put("shape", new ShapeHelp());
		this.cmdHelpMap.put("sel", new SelHelp());
		this.cmdHelpMap.put("genr", new GenrHelp());
		this.cmdHelpMap.put("phy", new PhyHelp());
		this.cmdHelpMap.put("shift", new ShiftHelp());
		this.cmdHelpMap.put("scale", new ScaleHelp());
		this.cmdHelpMap.put("mirror", new MirrorHelp());
		this.cmdHelpMap.put("rot", new RotHelp());
		this.cmdHelpMap.put("max", new MaxHelp());
		this.cmdHelpMap.put("min", new MinHelp());
		this.cmdHelpMap.put("del", new DelHelp());
		this.cmdHelpMap.put("undo", new UndoHelp());
		this.cmdHelpMap.put("redo", new RedoHelp());
	}
	
	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length == 0) {
			player.print("--- [" + cmdLogColor.main() + "Quick help for " + LogColor.RESET + "/sg] ---------------------");
			this.cmdHelpMap.entrySet().stream()
				.map(s -> s.getValue())
				.forEach(s -> player.print(cmdLogColor.main() + s.usage() + ": " + LogColor.RESET + s.description()));
			player.print(cmdLogColor.main() + "Run \"/sg help <subcommand>\" for details");
		}else if(args.length == 1) {
			CommandHelp cmdHelp = this.cmdHelpMap.get(args[0]);
			if(cmdHelp != null) {
				cmdHelp.toMultipleLines().stream()
					.forEach(s -> player.print(s));
			}else {
				player.print(cmdLogColor.error() + "Unknown subcommand");
			}
		}else {
			player.print(cmdLogColor.error() + "Usage: /sg help [subcommand]");
		}
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length){
			case 1 -> this.cmdHelpMap.keySet().stream().toList();
			default -> List.of();
		};
	}

}
