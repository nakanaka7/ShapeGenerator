package tokyo.nakanaka.shapeGenerator.sgSubcommandHelp;

public class HollowSphereCommandHelps {
    private HollowSphereCommandHelps(){
    }

    public static final BranchCommandHelp CENTER
            = new BranchCommandHelp.Builder("center")
            .description("Set center coordinate")
            .parameter("[x]", "x-coordinate")
            .parameter("[y]", "y-coordinate")
            .parameter("[z]", "z-coordinate")
            .build();

    public static final BranchCommandHelp OUTER_RADIUS
            = new BranchCommandHelp.Builder("outer_radius")
            .description("Set outer radius")
            .parameter("<outer_radius>", "outer radius")
            .build();

    public static final BranchCommandHelp INNER_RADIUS
            = new BranchCommandHelp.Builder("inner_radius")
            .description("Set inner radius")
            .parameter("<inner_radius>", "inner radius")
            .build();

}
