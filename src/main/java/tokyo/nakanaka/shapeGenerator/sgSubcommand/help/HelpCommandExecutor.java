package tokyo.nakanaka.shapeGenerator.sgSubcommand.help;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.SgSubcommandExecutor;
import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.CommandHelp;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.del.DelConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.genr.GenrConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.max.MaxConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.min.MinConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.mirror.MirrorConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.phy.PhyConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.redo.RedoConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.rot.RotConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.scale.ScaleConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.sel.SelConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.sel.SelHelp;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.shape.ShapeConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.shift.ShiftConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.undo.UndoConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.version.VersionConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.wand.WandConstants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static tokyo.nakanaka.shapeGenerator.SgSublabel.*;

public class HelpCommandExecutor implements SgSubcommandExecutor {
    private CommandLogColor cmdLogColor;
    private LinkedHashMap<String, CommandHelp> cmdHelpMap = new LinkedHashMap<>();

    public HelpCommandExecutor(CommandLogColor cmdLogColor) {
        this.cmdLogColor = cmdLogColor;
        this.cmdHelpMap.put(HELP, HelpConstants.HELP);
        this.cmdHelpMap.put(VERSION, VersionConstants.HELP);
        this.cmdHelpMap.put(WAND, WandConstants.HELP);
        this.cmdHelpMap.put(SHAPE, ShapeConstants.HELP);
        this.cmdHelpMap.put(SEL, SelConstants.HELP);
        this.cmdHelpMap.put(GENR, GenrConstants.HELP);
        this.cmdHelpMap.put(PHY, PhyConstants.HELP);
        this.cmdHelpMap.put(SHIFT, ShiftConstants.HELP);
        this.cmdHelpMap.put(SCALE, ScaleConstants.HELP);
        this.cmdHelpMap.put(MIRROR, MirrorConstants.HELP);
        this.cmdHelpMap.put(ROT, RotConstants.HELP);
        this.cmdHelpMap.put(MAX, MaxConstants.HELP);
        this.cmdHelpMap.put(MIN, MinConstants.HELP);
        this.cmdHelpMap.put(DEL, DelConstants.HELP);
        this.cmdHelpMap.put(UNDO, UndoConstants.HELP);
        this.cmdHelpMap.put(REDO, RedoConstants.HELP);
    }

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args) {
        String usage = "/sg help [subcommand]";
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
            return;
        }
        CommandHelp cmdHelp = this.cmdHelpMap.get(args[0]);
        if(cmdHelp == null) {
            player.print(cmdLogColor.error() + "Unknown subcommand");
            return;
        }
        if(cmdHelp instanceof BranchCommandHelp branchHelp){
            if(args.length != 1){
                player.print(cmdLogColor.error() + "Usage: " + usage);
                return;
            }
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
            if(args.length != 1){
                player.print(cmdLogColor.error() + "Usage: " + usage);
                return;
            }
            String[] labels = new String[]{"/sg", SEL};
            String desc = selHelp.description();
            List<SyntaxDesc> subcmdSyntaxDescs = new ArrayList<>();
            for(BranchCommandHelp e : selHelp.commonSubcommandHelps()){
                String subCmdSyntax = e.label() + " " + String.join(" ", e.parameterSyntaxes());
                subcmdSyntaxDescs.add(new SyntaxDesc(subCmdSyntax, e.description()));
            }
            for(BranchCommandHelp e : selHelp.shapeSubcommandHelps(playerData.getSelectionShape())){
                String subCmdSyntax = e.label() + " " + String.join(" ", e.parameterSyntaxes());
                subcmdSyntaxDescs.add(new SyntaxDesc(subCmdSyntax, e.description()));
            }
            List<String> lines = rootHelpMessage(cmdLogColor.main(), labels, desc, subcmdSyntaxDescs);
            lines.forEach(player::print);
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

}
