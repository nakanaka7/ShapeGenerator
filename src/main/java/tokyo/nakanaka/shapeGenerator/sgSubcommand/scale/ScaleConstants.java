package tokyo.nakanaka.shapeGenerator.sgSubcommand.scale;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class ScaleConstants {
    private ScaleConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Change the scale of the generated block(s)")
            .parameter("x|y|z", "an axis for scaling")
            .parameter("<factor>", "a factor for scaling")
            .build();

}
