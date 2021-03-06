package tokyo.nakanaka.shapeGenerator.commandHelp;

public class HollowConeCommandHelps {
    private HollowConeCommandHelps(){
    }

    public static final BranchCommandHelp CENTER
            = new BranchCommandHelp.Builder("center")
            .description("Set center of base disc")
            .parameter("[x]", "x-coordinate")
            .parameter("[y]", "y-coordinate")
            .parameter("[z]", "z-coordinate")
            .build();

    public static final BranchCommandHelp OUTER_RADIUS
            = new BranchCommandHelp.Builder("outer_radius")
            .description("Set outer radius of base disc")
            .parameter("<outer_radius>", "outer radius of base disc")
            .build();

    public static final BranchCommandHelp INNER_RADIUS
            = new BranchCommandHelp.Builder("inner_radius")
            .description("Set inner radius of base disc")
            .parameter("<inner_radius>", "inner radius of base disc")
            .build();

    public static final BranchCommandHelp HEIGHT
            = new BranchCommandHelp.Builder("height")
            .description("Set height")
            .parameter("<height>", "height")
            .build();

    public static final BranchCommandHelp DIRECTION
            = new BranchCommandHelp.Builder("direction")
            .description("Set direction")
            .parameter("north|south|east|west|up|down", "direction")
            .build();

}
