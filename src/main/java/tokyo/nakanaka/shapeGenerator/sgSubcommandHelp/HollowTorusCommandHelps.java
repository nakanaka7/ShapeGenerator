package tokyo.nakanaka.shapeGenerator.sgSubcommandHelp;

public class HollowTorusCommandHelps {

    public HollowTorusCommandHelps(){
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
            .description("Set major radius")
            .parameter("<radius>", "major radius")
            .build();

    public static final BranchCommandHelp OUTER_MINOR_RADIUS
            = new BranchCommandHelp.Builder("outer_minor_radius")
            .description("Set outer minor radius")
            .parameter("<radius>", "outer minor radius")
            .build();

    public static final BranchCommandHelp INNER_MINOR_RADIUS
            = new BranchCommandHelp.Builder("inner_minor_radius")
            .description("Set inner minor radius")
            .parameter("<radius>", "inner minor radius")
            .build();

    public static final BranchCommandHelp AXIS
            = new BranchCommandHelp.Builder("axis")
            .description("Set axis")
            .parameter("x|y|z", "axis direction")
            .build();

}
