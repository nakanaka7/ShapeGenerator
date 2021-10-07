package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import static tokyo.nakanaka.shapeGenerator.SgSublabel.*;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles "/sg help" command
 */
public class HelpCommandHandler implements SubCommandHandler {
	private LinkedHashMap<String, CommandHelp> cmdHelpMap = new LinkedHashMap<>();

	public HelpCommandHandler() {
		this.cmdHelpMap.put(HELP, SgBranchHelpConstants.HELP);
		this.cmdHelpMap.put(VERSION, SgBranchHelpConstants.VERSION);
		this.cmdHelpMap.put(WAND, SgBranchHelpConstants.WAND);
		this.cmdHelpMap.put(SHAPE, SgBranchHelpConstants.SHAPE);
		this.cmdHelpMap.put(SEL, new SelHelp());
		this.cmdHelpMap.put(GENR, SgBranchHelpConstants.GENR);
		this.cmdHelpMap.put(PHY, SgBranchHelpConstants.PHY);
		this.cmdHelpMap.put(SHIFT, SgBranchHelpConstants.SHIFT);
		this.cmdHelpMap.put(SCALE, SgBranchHelpConstants.SCALE);
		this.cmdHelpMap.put(MIRROR, SgBranchHelpConstants.MIRROR);
		this.cmdHelpMap.put(ROT, SgBranchHelpConstants.ROT);
		this.cmdHelpMap.put(MAX, SgBranchHelpConstants.MAX);
		this.cmdHelpMap.put(MIN, SgBranchHelpConstants.MIN);
		this.cmdHelpMap.put(DEL, SgBranchHelpConstants.DEL);
		this.cmdHelpMap.put(UNDO, SgBranchHelpConstants.UNDO);
		this.cmdHelpMap.put(REDO, SgBranchHelpConstants.REDO);
	}

	@Override
	public String label() {
		return "help";
	}

	@Override
	public String description() {
		return "Print the command help";
	}

	public ParameterUsage[] parameterUsages() {
		var subcmd = new ParameterUsage("[subcommand]", "a subcommand to print the help");
		return new ParameterUsage[]{subcmd};
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
		if(args.length == 0) {
			String[] labels = new String[]{"/sg"};
			String desc = "The root command of ShapeGenerator";
			List<SyntaxDesc> subcmdSyntaxDescs = new ArrayList<>();
			for (Map.Entry<String, CommandHelp> e : this.cmdHelpMap.entrySet()){
				String subCmdSyntax = e.getKey() + " " + String.join(" ", e.getValue().parameterSyntaxes());
				subcmdSyntaxDescs.add(new SyntaxDesc(subCmdSyntax , e.getValue().description()));
			}
			List<String> lines = rootHelpMessage(cmdLogColor.main(), labels, desc, subcmdSyntaxDescs);
			lines.add(cmdLogColor.main() + "Run \"/sg help <subcommand>\" for details");
			lines.forEach(player::print);
		}else if(args.length == 1) {
			CommandHelp cmdHelp = this.cmdHelpMap.get(args[0]);
			if(cmdHelp == null) {
				player.print(cmdLogColor.error() + "Unknown subcommand");
				return;
			}
			if(cmdHelp instanceof BranchCommandHelp branchHelp){
				String[] labels = new String[]{"/sg", args[0]};
				String desc = branchHelp.description();
				List<SyntaxDesc> paramSyntaxDescs = new ArrayList<>();
				for(int i = 0; i < branchHelp.parameterSize(); i++){
					String paramSyntax = branchHelp.parameterSyntaxes()[i];
					String paramDesc = branchHelp.parameterDescriptions()[i];
					paramSyntaxDescs.add(new SyntaxDesc(paramSyntax, paramDesc));
				}
				List<String> lines = branchHelpMessage(cmdLogColor.main(), labels, desc, paramSyntaxDescs);
				lines.forEach(player::print);
			}else if(cmdHelp instanceof SelHelp selHelp){
				String[] labels = new String[]{"/sg", SEL};
				String desc = selHelp.description();
				selHelp.toMultipleLines(playerData.getSelectionShape()).forEach(player::print);
			}
		}else {
			player.print(cmdLogColor.error() + "Usage: /sg help [subcommand]");
		}
	}

	private static record SyntaxDesc(String syntax, String desc) {
	}

	private static List<String> rootHelpMessage(LogColor mainColor, String[] labels, String desc, List<SyntaxDesc> subcmdSyntaxDescs) {
		List<String> lines = new ArrayList<>();
		String head = String.join(" ", labels);
		lines.add(MessageUtils.title(mainColor + "Help for " + LogColor.RESET + head));
		lines.add(mainColor + "Description: " + LogColor.RESET + desc);
		lines.add(mainColor + "Usage: " + LogColor.RESET + head + mainColor + " <subcommand>");
		if(subcmdSyntaxDescs.size() != 0){
			lines.add(mainColor + "Subcommand: ");
			for(SyntaxDesc sd : subcmdSyntaxDescs){
				lines.add("  " + mainColor + sd.syntax + ": " + LogColor.RESET + sd.desc);
			}
		}
		return lines;
	}

	private static List<String> branchHelpMessage(LogColor mainColor, String[] labels, String desc, List<SyntaxDesc> paramSyntaxDescs) {
		List<String> lines = new ArrayList<>();
		String head = String.join(" ", labels);
		lines.add(MessageUtils.title(mainColor + "Help for " + LogColor.RESET + head));
		lines.add(mainColor + "Description: " + LogColor.RESET + desc);
		String paramUsage = String.join(" ", paramSyntaxDescs.stream().map(SyntaxDesc::syntax).toList());
		lines.add(mainColor + "Usage: " + LogColor.RESET + head + " " + paramUsage);
		if(paramSyntaxDescs.size() != 0){
			lines.add(mainColor + "Parameter: ");
			for(SyntaxDesc sd : paramSyntaxDescs){
				lines.add("  " + mainColor + sd.syntax + ": " + LogColor.RESET + sd.desc);
			}
		}
		return lines;
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length){
			case 1 -> this.cmdHelpMap.keySet().stream().toList();
			default -> List.of();
		};
	}

}
