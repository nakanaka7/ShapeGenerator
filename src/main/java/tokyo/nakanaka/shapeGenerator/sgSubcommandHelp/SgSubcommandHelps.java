package tokyo.nakanaka.shapeGenerator.sgSubcommandHelp;

public class SgSubcommandHelps {
    private SgSubcommandHelps() {
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Print the command help")
            .parameter("[subcommand]", "a subcommand to print the help")
            .build();

    public static final BranchCommandHelp VERSION
            = new BranchCommandHelp.Builder()
            .description("Print the version")
            .build();

    public static final BranchCommandHelp WAND
            = new BranchCommandHelp.Builder()
            .description("Give a player a wand")
            .build();

    public static final BranchCommandHelp WANDHELP
            = new BranchCommandHelp.Builder()
            .description("Print wand help")
            .parameter("<shape>", "a selection shape")
            .build();

    public static final BranchCommandHelp SHAPE
            = new BranchCommandHelp.Builder()
            .description("Set selection shape")
            .parameter("<type>", "a shape type")
            .build();

    public static final SelHelp SEL = new SelHelp();

    public static final BranchCommandHelp GENR
            = new BranchCommandHelp.Builder()
            .description("Generate block(s) in the selection")
            .parameter("<block>", "the block to generate")
            .build();

    public static final BranchCommandHelp PHY
            = new BranchCommandHelp.Builder()
            .description("Toggle physics option for generating blocks")
            .parameter("true|false", "an option for physics")
            .build();

    public static final BranchCommandHelp SHIFT
            = new BranchCommandHelp.Builder()
            .description("Shift the generated blocks")
            .parameter("<direction>", "the direction to shift")
            .parameter("<distance>", "distance of shift")
            .build();

    public static final BranchCommandHelp SCALE
            = new BranchCommandHelp.Builder()
            .description("Change the scale of the generated block(s)")
            .parameter("x|y|z", "an axis for scaling")
            .parameter("<factor>", "a factor for scaling")
            .build();

    public static final BranchCommandHelp MIRROR
            = new BranchCommandHelp.Builder()
            .description("Mirror the generated blocks")
            .parameter("x|y|z", "an axis to mirror")
            .build();

    public static final BranchCommandHelp ROT
            = new BranchCommandHelp.Builder()
            .description("Rotate the generated block(s)")
            .parameter("x|y|z", "an axis for rotating")
            .parameter("<degree>", "a degree for rotating")
            .build();

    public static final BranchCommandHelp MAX
            = new BranchCommandHelp.Builder()
            .description("Set max coordinate of the generated blocks")
            .parameter("x|y|z", "axis")
            .parameter("<coordinate>", "maximum coordinate of the generation")
            .build();

    public static final BranchCommandHelp MIN
            = new BranchCommandHelp.Builder()
            .description("Set min coordinate of the generated blocks")
            .parameter("x|y|z", "axis")
            .parameter("<coordinate>", "minimum coordinate of the generation")
            .build();

    public static final BranchCommandHelp DEL
            = new BranchCommandHelp.Builder()
            .description("Delete the generated block(s)")
            .parameter("[number]", "a number to delete generation(s)")
            .build();

    public static final BranchCommandHelp UNDO
            = new BranchCommandHelp.Builder()
            .description("Undo block changing command(s)")
            .parameter("[number]", "a number to undo generation(s)")
            .build();

    public static final BranchCommandHelp REDO
            = new BranchCommandHelp.Builder()
            .description("Redo block changing command(s)")
            .parameter("[number]", "a number to redo generation(s)")
            .build();

}
