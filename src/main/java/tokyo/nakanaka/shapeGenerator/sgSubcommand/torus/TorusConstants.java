package tokyo.nakanaka.shapeGenerator.sgSubcommand.torus;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class TorusConstants {
    private TorusConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Generate torus")
            .parameter("<major radius>", "major radius")
            .parameter("<minor radius>", "minor radius")
            .parameter("x|y|z", "axis")
            .build();

}
