package tokyo.nakanaka.shapeGenerator.sgSubcommandHelp;

import java.util.ArrayList;
import java.util.List;

public class BranchCommandHelp implements CommandHelp {
    private String label;
    private String desc;
    private List<String> paramSyntaxList;
    private List<String> paramDescList;

    private BranchCommandHelp(Builder builder) {
        this.label = builder.label;
        this.desc = builder.desc;
        this.paramSyntaxList = builder.paramSyntaxList;
        this.paramDescList = builder.paramDescList;
    }

    public static class Builder {
        private String label = "";
        private String desc = "";
        private List<String> paramSyntaxList = new ArrayList<>();
        private List<String> paramDescList = new ArrayList<>();

        public Builder(String label){
            this.label = label;
        }

        public Builder(){
            this("");
        }

        /**
         * @param desc the description of the command
         */
        public Builder description(String desc) {
            this.desc = desc;
            return this;
        }

        /**
         * @param syntax the syntax of the parameter
         * @param desc the description of the parameter
         */
        public Builder parameter(String syntax, String desc) {
            this.paramSyntaxList.add(syntax);
            this.paramDescList.add(desc);
            return  this;
        }

        public BranchCommandHelp build() {
            return new BranchCommandHelp(this);
        }

    }

    public String label(){
        return this.label;
    }

    /**
     * Returns the description of this command
     * @return the description of this command
     */
    public String description() {
        return this.desc;
    }

    public int parameterSize() {
        return this.paramSyntaxList.size();
    }

    @Override
    public String[] parameterSyntaxes() {
        return this.paramSyntaxList.toArray(new String[this.paramSyntaxList.size()]);
    }

    /**
     * Returns the descriptions of parameters
     * @return the descriptions of parameters
     */
    public String[] parameterDescriptions(){
        return this.paramDescList.toArray(new String[this.paramDescList.size()]);
    }

}
