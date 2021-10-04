package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class BranchHelpMessageCreator {
    private LogColor color;
    private String[] labels;
    private String desc;
    private String[] paramSyntax;
    private String[] paramDesc;

    public BranchHelpMessageCreator(Builder builder) {
        this.color = builder.color;
        this.labels = builder.labels;
        this.desc = builder.desc;
        this.paramSyntax = builder.paramSyntaxList
                .toArray(new String[builder.paramSyntaxList.size()]);
        this.paramDesc = builder.paramDescList
                .toArray(new String[builder.paramDescList.size()]);
    }

    public static class Builder {
        private LogColor color;
        private String[] labels;
        private String desc = "";
        private List<String> paramSyntaxList = new ArrayList<>();
        private List<String> paramDescList = new ArrayList<>();

        public Builder(LogColor color, String... labels) {
            this.color = color;
            this.labels = labels;
        }

        public Builder description(String desc){
            this.desc = desc;
            return this;
        }

        public Builder parameter(String syntax, String desc){
            this.paramSyntaxList.add(syntax);
            this.paramDescList.add(desc);
            return this;
        }

        public BranchHelpMessageCreator build() {
            return new BranchHelpMessageCreator(this);
        }

    }

    public List<String> toMessageLines() {
        List<String> lines = new ArrayList<>();
        String head = String.join(" ", labels);
        lines.add(MessageUtils.title(color + "Help for " + LogColor.RESET + head));
        lines.add(color + "Description: " + LogColor.RESET + desc);
        lines.add(color + "Usage: " + LogColor.RESET + head + " " + color + String.join(" ", paramSyntax));
        if(paramSyntax.length != 0){
            lines.add(color + "Parameter: ");
            for(int i = 0; i < paramSyntax.length; ++i){
                lines.add("  " + color + paramSyntax[i] + ": " + LogColor.RESET + paramDesc[i]);
            }
        }
        return lines;
    }


}
