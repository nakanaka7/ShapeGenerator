package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

public class BranchCommandHelp implements CommandHelp {
    private String label;
    private String desc;
    private List<String> paramSyntaxList;
    private List<String> paramDescList;

    private BranchCommandHelp(String label, String desc, List<String> paramSyntaxList, List<String> paramDescList) {
        this.label = label;
        this.desc = desc;
        this.paramSyntaxList = paramSyntaxList;
        this.paramDescList = paramDescList;
    }

    public static class Builder {
        private String label;
        private String desc = "";
        private List<String> paramSyntaxList = new ArrayList<>();
        private List<String> paramDescList = new ArrayList<>();

        /**
         * @param label the command label
         */
        public Builder(String label) {
            this.label = label;
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
            return new BranchCommandHelp(label, desc, paramSyntaxList, paramDescList);
        }

    }

    @Override
    public String label() {
        return this.label;
    }

    /**
     * Returns the command syntax
     * @return the command syntax
     */
    public String syntax() {
       String syntax = label;
       for(String paramSyntax : this.paramSyntaxList){
           syntax += " " + paramSyntax;
       }
       return syntax;
    }

    /**
     * Returns the description of this command
     * @return the description of this command
     */
    public String description() {
        return this.desc;
    }

    @Override
    public String[] parameterSyntaxes() {
        String[] array = new String[this.paramSyntaxList.size()];
        for (int i = 0; i < this.paramSyntaxList.size(); ++i) {
            array[i] = this.paramSyntaxList.get(i);
        }
        return array;
    }

    /**
     * Returns the description of a parameter
     * @param index the index of a parameter
     * @return the description of a parameter
     * @throws IllegalArgumentException if the index is out of range
     */
    public String parameterDescription(int index) {
        if(index < 0 || index >= this.paramDescList.size()) {
            throw new IllegalArgumentException();
        }
        return this.paramDescList.get(index);
    }

}
