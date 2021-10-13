package tokyo.nakanaka.shapeGenerator.sgSubcommand.help;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.SgSubcommandExecutor;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.CommandHelp;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.del.DelConstants;
import tokyo.nakanaka.shapeGenerator.sgSubcommand.sel.SelHelp;
import tokyo.nakanaka.shapeGenerator.commandHelp.SgSubcommandHelps;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static tokyo.nakanaka.shapeGenerator.SgSublabel.*;
import static tokyo.nakanaka.shapeGenerator.SgSublabel.REDO;

public class HelpCommandExecutor implements SgSubcommandExecutor {
    private LinkedHashMap<String, CommandHelp> cmdHelpMap = new LinkedHashMap<>();

    public HelpCommandExecutor() {
        this.cmdHelpMap.put(HELP, HelpConstants.HELP);
        this.cmdHelpMap.put(VERSION, SgSubcommandHelps.VERSION);
        this.cmdHelpMap.put(WAND, SgSubcommandHelps.WAND);
        this.cmdHelpMap.put(SHAPE, SgSubcommandHelps.SHAPE);
        this.cmdHelpMap.put(SEL, SgSubcommandHelps.SEL);
        this.cmdHelpMap.put(GENR, SgSubcommandHelps.GENR);
        this.cmdHelpMap.put(PHY, SgSubcommandHelps.PHY);
        this.cmdHelpMap.put(SHIFT, SgSubcommandHelps.SHIFT);
        this.cmdHelpMap.put(SCALE, SgSubcommandHelps.SCALE);
        this.cmdHelpMap.put(MIRROR, SgSubcommandHelps.MIRROR);
        this.cmdHelpMap.put(ROT, SgSubcommandHelps.ROT);
        this.cmdHelpMap.put(MAX, SgSubcommandHelps.MAX);
        this.cmdHelpMap.put(MIN, SgSubcommandHelps.MIN);
        this.cmdHelpMap.put(DEL, DelConstants.DEL_HELP);
        this.cmdHelpMap.put(UNDO, SgSubcommandHelps.UNDO);
        this.cmdHelpMap.put(REDO, SgSubcommandHelps.REDO);
    }

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
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