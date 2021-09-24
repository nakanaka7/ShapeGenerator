package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.CommandHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.DelHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.GenrHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.HelpHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.MaxHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.MinHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.MirrorHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.PhyHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.RedoHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.RotHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ScaleHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SelHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ShapeHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ShiftHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.UndoHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.VersionHelp;

/**
 * Handles "/sg help" command
 */
public class HelpCommandHandler implements SubCommandHandler {
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
			player.print("--- [" + LogColor.GOLD + "Quick help for " + LogColor.RESET + "/sg] ---------------------");
			this.cmdHelpMap.entrySet().stream()
				.map(s -> s.getValue())
				.forEach(s -> player.print(s.toSingleLine()));
			player.print(LogColor.GOLD + "Run \"/sg help <subcommand>\" for details");
		}else if(args.length == 1) {
			CommandHelp cmdHelp = this.cmdHelpMap.get(args[0]);
			if(cmdHelp != null) {
				cmdHelp.toMultipleLines().stream()
					.forEach(s -> player.print(s));
			}else {
				player.print(LogColor.RED + "Unknown subcommand");
			}
		}else {
			player.print(LogColor.RED + "Usage: /sg help [subcommand]");
		}
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		if(args.length == 1) {
			return new ArrayList<>(this.cmdHelpMap.keySet());
		}else {
			return List.of();
		}
	}

}
