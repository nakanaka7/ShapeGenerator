package tokyo.nakanaka.shapeGenerator.commandHelp;

public class CuboidCommandHelps {
    private CuboidCommandHelps(){
    }

    public static final BranchCommandHelp POS1
            = new BranchCommandHelp.Builder("pos1")
            .description("Set pos1 coordinate")
            .parameter("[x]", "x-coordinate")
            .parameter("[y]", "y-coordinate")
            .parameter("[z]", "z-coordinate")
            .build();

    public static final BranchCommandHelp POS2
            = new BranchCommandHelp.Builder("pos2")
            .description("Set pos2 coordinate")
            .parameter("[x]", "x-coordinate")
            .parameter("[y]", "y-coordinate")
            .parameter("[z]", "z-coordinate")
            .build();

}
