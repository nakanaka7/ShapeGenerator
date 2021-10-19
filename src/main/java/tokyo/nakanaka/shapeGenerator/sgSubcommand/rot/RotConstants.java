package tokyo.nakanaka.shapeGenerator.sgSubcommand.rot;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class RotConstants {
    private RotConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Rotate the generated block(s)")
            .parameter("x|y|z", "an axis for rotating")
            .parameter("<degree>", "a degree for rotating")
            .build();

}
