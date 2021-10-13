package tokyo.nakanaka.shapeGenerator.sgSubcommandHelp;

public class RegularPrismCommandHelps {
    private RegularPrismCommandHelps(){
    }

    public static final BranchCommandHelp CENTER
            = new BranchCommandHelp.Builder("center")
            .description("Set center of base regular polygon")
            .parameter("[x]", "x-coordinate")
            .parameter("[y]", "y-coordinate")
            .parameter("[z]", "z-coordinate")
            .build();

    public static final BranchCommandHelp RADIUS
            = new BranchCommandHelp.Builder("radius")
            .description("Set radius of base regular polygon")
            .parameter("<radius>", "radius")
            .build();

    public static final BranchCommandHelp SIDE
            = new BranchCommandHelp.Builder("side")
            .description("Set side numbers of base regular polygon")
            .parameter("<number>", "side number")
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
