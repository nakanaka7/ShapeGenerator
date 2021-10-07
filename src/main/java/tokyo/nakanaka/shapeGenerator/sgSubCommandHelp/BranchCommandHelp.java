package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

public class BranchCommandHelp implements CommandHelp {
    private String desc;
    private List<String> paramSyntaxList;
    private List<String> paramDescList;

    private BranchCommandHelp(String desc, List<String> paramSyntaxList, List<String> paramDescList) {
        this.desc = desc;
        this.paramSyntaxList = paramSyntaxList;
        this.paramDescList = paramDescList;
    }

    public static class Builder {
        private String desc = "";
        private List<String> paramSyntaxList = new ArrayList<>();
        private List<String> paramDescList = new ArrayList<>();

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
            return new BranchCommandHelp(desc, paramSyntaxList, paramDescList);
        }

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
