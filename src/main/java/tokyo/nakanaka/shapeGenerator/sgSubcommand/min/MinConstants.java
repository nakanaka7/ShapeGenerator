package tokyo.nakanaka.shapeGenerator.sgSubcommand.min;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class MinConstants {

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Set min coordinate of the generated blocks")
            .parameter("x|y|z", "axis")
            .parameter("<coordinate>", "minimum coordinate of the generation")
            .build();

}
