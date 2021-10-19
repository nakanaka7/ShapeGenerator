package tokyo.nakanaka.shapeGenerator.sgSubcommand.version;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class VersionConstants {
    private VersionConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Print the version")
            .build();

}
