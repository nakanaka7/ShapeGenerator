package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

public interface BranchCommandHelp extends CommandHelp{
    /**
     * Returns parameter usages
     * @return parameter usages
     */
    String[] parameterUsages();

    /**
     * Returns parameter descriptions
     * @return parameter descriptions
     */
    String[] parameterDescriptions();

}
