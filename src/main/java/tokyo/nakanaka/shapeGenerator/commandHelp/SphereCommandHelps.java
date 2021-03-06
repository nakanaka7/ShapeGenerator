package tokyo.nakanaka.shapeGenerator.commandHelp;

public class SphereCommandHelps {
    private SphereCommandHelps() {
    }

    public static final BranchCommandHelp CENTER
            = new BranchCommandHelp.Builder("center")
            .description("Set center coordinate")
            .parameter("[x]", "x-coordinate")
            .parameter("[y]", "y-coordinate")
            .parameter("[z]", "z-coordinate")
            .build();

    public static final BranchCommandHelp RADIUS
            = new BranchCommandHelp.Builder("radius")
            .description("Set radius")
            .parameter("<radius>", "radius")
            .build();

}
