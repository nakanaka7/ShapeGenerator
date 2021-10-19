package tokyo.nakanaka.shapeGenerator.sgSubcommand.mirror;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class MirrorConstants {
    private MirrorConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Mirror the generated blocks")
            .parameter("x|y|z", "an axis to mirror")
            .build();

}
