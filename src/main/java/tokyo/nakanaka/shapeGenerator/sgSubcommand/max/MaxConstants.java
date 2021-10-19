package tokyo.nakanaka.shapeGenerator.sgSubcommand.max;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class MaxConstants {
    private MaxConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Set max coordinate of the generated blocks")
            .parameter("x|y|z", "axis")
            .parameter("<coordinate>", "maximum coordinate of the generation")
            .build();

}
