package tokyo.nakanaka.shapeGenerator.sgSubcommand.help;

import tokyo.nakanaka.shapeGenerator.sgSubcommandHelp.BranchCommandHelp;

public class HelpConstants {
    private HelpConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder("help")
            .description("Print the command help")
            .parameter("[subcommand]", "a subcommand to print the help")
            .build();

}
