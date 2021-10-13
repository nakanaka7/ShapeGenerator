package tokyo.nakanaka.shapeGenerator.sgSubcommand.del;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class DelConstants {
    private DelConstants(){
    }

    public static final BranchCommandHelp DEL_HELP
            = new BranchCommandHelp.Builder()
            .description("Delete the generated block(s)")
            .parameter("[number]", "a number to delete generation(s)")
            .build();

}
