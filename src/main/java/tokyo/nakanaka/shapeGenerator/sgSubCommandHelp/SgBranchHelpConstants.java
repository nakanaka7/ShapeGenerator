package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

public class SgBranchHelpConstants {
    private SgBranchHelpConstants() {
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder("/sg", "help")
            .description("Print the command help")
            .parameter("[subcommand]", "a subcommand to print the help")
            .build();

    public static final BranchCommandHelp VERSION
            = new BranchCommandHelp.Builder("/sg", "version")
            .description("Print the version")
            .build();

    public static final BranchCommandHelp WAND
            = new BranchCommandHelp.Builder("/sg", "wand")
            .description("Give a player a wand")
            .build();

    public static final BranchCommandHelp SHAPE
            = new BranchCommandHelp.Builder("/sg", "shape")
            .description("Set selection shape")
            .parameter("<type>", "a shape type")
            .build();

    public static final BranchCommandHelp GENR
            = new BranchCommandHelp.Builder("/sg", "genr")
            .description("Generate block(s) in the selection")
            .parameter("<block>", "the block to generate")
            .build();

    public static final BranchCommandHelp PHY
            = new BranchCommandHelp.Builder("/sg", "phy")
            .description("Toggle physics option for generating blocks")
            .parameter("true|false", "an option for physics")
            .build();

    public static final BranchCommandHelp SHIFT
            = new BranchCommandHelp.Builder("/sg", "shift")
            .description("Shift the generated blocks")
            .parameter("<direction>", "the direction to shift")
            .parameter("<distance>", "distance of shift")
            .build();

    public static final BranchCommandHelp SCALE
            = new BranchCommandHelp.Builder("/sg", "scale")
            .description("Change the scale of the generated block(s)")
            .parameter("x|y|z", "an axis for scaling")
            .parameter("<factor>", "a factor for scaling")
            .build();

    public static final BranchCommandHelp MIRROR
            = new BranchCommandHelp.Builder("/sg", "mirror")
            .description("Mirror the generated blocks")
            .parameter("x|y|z", "an axis to mirror")
            .build();

    public static final BranchCommandHelp ROT
            = new BranchCommandHelp.Builder("/sg", "rot")
            .description("Rotate the generated block(s)")
            .parameter("x|y|z", "an axis for rotating")
            .parameter("<degree>", "a degree for rotating")
            .build();

    public static final BranchCommandHelp MAX
            = new BranchCommandHelp.Builder("/sg", "max")
            .description("Set max coordinate of the generated blocks")
            .parameter("x|y|z", "axis")
            .parameter("<coordinate>", "maximum coordinate of the generation")
            .build();

    public static final BranchCommandHelp MIN
            = new BranchCommandHelp.Builder("/sg", "min")
            .description("Set min coordinate of the generated blocks")
            .parameter("x|y|z", "axis")
            .parameter("<coordinate>", "minimum coordinate of the generation")
            .build();

    public static final BranchCommandHelp DEL
            = new BranchCommandHelp.Builder("/sg", "del")
            .description("Delete the generated block(s)")
            .parameter("[number]", "a number to delete generation(s)")
            .build();

    public static final BranchCommandHelp UNDO
            = new BranchCommandHelp.Builder("/sg", "undo")
            .description("Undo block changing command(s)")
            .parameter("[number]", "a number to undo generation(s)")
            .build();

    public static final BranchCommandHelp REDO
            = new BranchCommandHelp.Builder("/sg", "redo")
            .description("Redo block changing command(s)")
            .parameter("[number]", "a number to redo generation(s)")
            .build();

}
