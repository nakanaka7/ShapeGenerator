package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class RootHelpMessageCreator {
    private LogColor color;
    private String[] labels;
    private String desc;
    private String[] subcmdSyntax;
    private String[] subcmdDesc;

    public RootHelpMessageCreator(Builder builder) {
        this.color = builder.color;
        this.labels = builder.labels;
        this.desc = builder.desc;
        this.subcmdSyntax = builder.subCmdSyntaxList
                .toArray(new String[builder.subCmdSyntaxList.size()]);
        this.subcmdDesc = builder.subCmdDescList
                .toArray(new String[builder.subCmdDescList.size()]);
    }

    public static class Builder {
        private LogColor color;
        private String[] labels;
        private String desc = "";
        private List<String> subCmdSyntaxList = new ArrayList<>();
        private List<String> subCmdDescList = new ArrayList<>();

        public Builder(LogColor color, String... labels) {
            this.color = color;
            this.labels = labels;
        }

        public Builder description(String desc){
            this.desc = desc;
            return this;
        }

        public Builder subcommand(String syntax, String desc){
            this.subCmdSyntaxList.add(syntax);
            this.subCmdDescList.add(desc);
            return this;
        }

        public RootHelpMessageCreator build() {
            return new RootHelpMessageCreator(this);
        }

    }

    public List<String> toMessageLines() {
        List<String> lines = new ArrayList<>();
        String head = String.join(" ", labels);
        lines.add(MessageUtils.title(color + "Help for " + LogColor.RESET + head));
        lines.add(color + "Description: " + LogColor.RESET + desc);
        lines.add(color + "Usage: " + LogColor.RESET + head + color + " <subcommand>");
        if(subcmdSyntax.length != 0){
            lines.add(color + "Subcommand: ");
            for(int i = 0; i < subcmdSyntax.length; ++i){
                lines.add("  " + color + subcmdSyntax[i] + ": " + LogColor.RESET + subcmdDesc[i]);
            }
        }
        return lines;
    }

}
