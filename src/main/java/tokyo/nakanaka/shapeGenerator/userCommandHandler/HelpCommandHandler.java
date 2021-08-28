package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.commandHelp.CommandHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.DelHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.GenrHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.HelpHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.MaxxHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.MaxyHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.MaxzHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.MinxHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.MinyHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.MinzHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.MirrorHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.PhyHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.RedoHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.RotHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.ScaleHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.SelHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.ShapeHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.ShiftHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.UndoHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.WandHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg help" command
 */
public class HelpCommandHandler implements UserCommandHandler {
	private LinkedHashMap<String, CommandHelp> cmdHelpMap = new LinkedHashMap<>();
	
	public HelpCommandHandler() {
		this.cmdHelpMap.put("help", new HelpHelp());
		this.cmdHelpMap.put("wand", new WandHelp());
		this.cmdHelpMap.put("shape", new ShapeHelp());
		this.cmdHelpMap.put("sel", new SelHelp());
		this.cmdHelpMap.put("genr", new GenrHelp());
		this.cmdHelpMap.put("phy", new PhyHelp());
		this.cmdHelpMap.put("shift", new ShiftHelp());
		this.cmdHelpMap.put("scale", new ScaleHelp());
		this.cmdHelpMap.put("mirror", new MirrorHelp());
		this.cmdHelpMap.put("rot", new RotHelp());
		this.cmdHelpMap.put("maxx", new MaxxHelp());
		this.cmdHelpMap.put("maxy", new MaxyHelp());
		this.cmdHelpMap.put("maxz", new MaxzHelp());
		this.cmdHelpMap.put("minx", new MinxHelp());
		this.cmdHelpMap.put("miny", new MinyHelp());
		this.cmdHelpMap.put("minz", new MinzHelp());
		this.cmdHelpMap.put("del", new DelHelp());
		this.cmdHelpMap.put("undo", new UndoHelp());
		this.cmdHelpMap.put("redo", new RedoHelp());
	}
	
	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
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
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		if(args.length == 1) {
			return new ArrayList<>(this.cmdHelpMap.keySet());
		}else {
			return List.of();
		}
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
