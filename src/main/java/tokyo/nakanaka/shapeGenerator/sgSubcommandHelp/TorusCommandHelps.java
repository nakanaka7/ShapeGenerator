package tokyo.nakanaka.shapeGenerator.sgSubcommandHelp;

public class TorusCommandHelps {
    private TorusCommandHelps(){
    }

    public static final BranchCommandHelp CENTER
            = new BranchCommandHelp.Builder("center")
            .description("Set center coordinate")
            .parameter("[x]", "x-coordinate")
            .parameter("[y]", "y-coordinate")
            .parameter("[z]", "z-coordinate")
            .build();

    public static final BranchCommandHelp MAJOR_RADIUS
            = new BranchCommandHelp.Builder("major_radius")
            .description("Set major_radius")
            .parameter("<major_radius>", "major_radius")
            .build();

    public static final BranchCommandHelp MINOR_RADIUS
            = new BranchCommandHelp.Builder("minor_radius")
            .description("Set minor_radius")
            .parameter("<minor_radius>", "minor_radius")
            .build();

    public static final BranchCommandHelp AXIS
            = new BranchCommandHelp.Builder("axis")
            .description("Set axis")
            .parameter("x|y|z", "axis direction")
            .build();

}
