package tokyo.nakanaka.shapeGenerator.sgSubcommand.shape;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class ShapeConstants {
    private ShapeConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Set selection shape")
            .parameter("<type>", "a shape type")
            .build();

}
