package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

public class TriangleCommandHelps {

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

    public static final BranchCommandHelp POS3
            = new BranchCommandHelp.Builder("pos3")
            .description("Set pos3 coordinate")
            .parameter("[x]", "x-coordinate")
            .parameter("[y]", "y-coordinate")
            .parameter("[z]", "z-coordinate")
            .build();

    public static final BranchCommandHelp THICKNESS
            = new BranchCommandHelp.Builder("thickness")
            .description("Set thickness")
            .parameter("<thickness>", "thickness")
            .build();

}
