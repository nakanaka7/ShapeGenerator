package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

public class DiamondCommandHelps {
    private DiamondCommandHelps(){
    }

    public static final BranchCommandHelp CENTER
            = new BranchCommandHelp.Builder("center")
            .description("Set center coordinate")
            .parameter("[x]", "x-coordinate")
            .parameter("[y]", "y-coordinate")
            .parameter("[z]", "z-coordinate")
            .build();

    public static final BranchCommandHelp WIDTH
            = new BranchCommandHelp.Builder("width")
            .description("Set width")
            .parameter("<width>", "width")
            .build();

    public static final BranchCommandHelp HEIGHT
            = new BranchCommandHelp.Builder("height")
            .description("Set height")
            .parameter("<height>", "height")
            .build();

    public static final BranchCommandHelp LENGTH
            = new BranchCommandHelp.Builder("length")
            .description("Set length")
            .parameter("<length>", "length")
            .build();

}
