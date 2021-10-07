package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class RootHelpMessage {
    private String[] labels;
    private String desc = "";
    private List<String> subcmdSyntaxList = new ArrayList<>();
    private List<String> subcmdDescList = new ArrayList<>();

    public RootHelpMessage(String... labels) {
        this.labels = labels;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public void addSubcommand(String syntax, String desc){
        this.subcmdSyntaxList.add(syntax);
        this.subcmdDescList.add(desc);
    }

    public List<String> toLines(LogColor color) {
        List<String> lines = new ArrayList<>();
        String head = String.join(" ", labels);
        lines.add(MessageUtils.title(color + "Help for " + LogColor.RESET + head));
        lines.add(color + "Description: " + LogColor.RESET + desc);
        lines.add(color + "Usage: " + LogColor.RESET + head + color + " <subcommand>");
        if(subcmdSyntaxList.size() != 0){
            lines.add(color + "Subcommand: ");
            for(int i = 0; i < subcmdSyntaxList.size(); ++i){
                lines.add("  " + color + subcmdSyntaxList.get(i) + ": " + LogColor.RESET + subcmdDescList.get(i));
            }
        }
        return lines;
    }

}
