package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

public class SgBranchHelpConstants {
    private SgBranchHelpConstants() {
    }

    public static final BranchCommandHelpNew VERSION
            = new BranchCommandHelpNew.Builder("/sg", "version")
            .description("Print the version")
            .build();

    public static final BranchCommandHelpNew SHAPE
            = new BranchCommandHelpNew.Builder("/sg", "shape")
            .description("Set selection shape")
            .parameter("<type>", "a shape type")
            .build();

    public static final BranchCommandHelpNew GENR
            = new BranchCommandHelpNew.Builder("/sg", "genr")
            .description("Generate block(s) in the selection")
            .parameter("<block>", "the block to generate")
            .build();

    public static final BranchCommandHelpNew PHY
            = new BranchCommandHelpNew.Builder("/sg", "phy")
            .description("Toggle physics option for generating blocks")
            .parameter("true|false", "an option for physics")
            .build();

    public static final BranchCommandHelpNew SHIFT
            = new BranchCommandHelpNew.Builder("/sg", "shift")
            .description("Shift the generated blocks")
            .parameter("<direction>", "the direction to shift")
            .parameter("<distance>", "distance of shift")
            .build();

    public static final BranchCommandHelpNew SCALE
            = new BranchCommandHelpNew.Builder("/sg", "scale")
            .description("Change the scale of the generated block(s)")
            .parameter("x|y|z", "an axis for scaling")
            .parameter("<factor>", "a factor for scaling")
            .build();

    public static final BranchCommandHelpNew MIRROR
            = new BranchCommandHelpNew.Builder("/sg", "mirror")
            .description("Mirror the generated blocks")
            .parameter("x|y|z", "an axis to mirror")
            .build();

    public static final BranchCommandHelpNew ROT
            = new BranchCommandHelpNew.Builder("/sg", "rot")
            .description("Rotate the generated block(s)")
            .parameter("x|y|z", "an axis for rotating")
            .parameter("<degree>", "a degree for rotating")
            .build();

    public static final BranchCommandHelpNew MAX
            = new BranchCommandHelpNew.Builder("/sg", "max")
            .description("Set max coordinate of the generated blocks")
            .parameter("x|y|z", "axis")
            .parameter("<coordinate>", "maximum coordinate of the generation")
            .build();

    public static final BranchCommandHelpNew MIN
            = new BranchCommandHelpNew.Builder("/sg", "min")
            .description("Set min coordinate of the generated blocks")
            .parameter("x|y|z", "axis")
            .parameter("<coordinate>", "minimum coordinate of the generation")
            .build();

    public static final BranchCommandHelpNew DEL
            = new BranchCommandHelpNew.Builder("/sg", "del")
            .description("Delete the generated block(s)")
            .parameter("[number]", "a number to delete generation(s)")
            .build();

    public static final BranchCommandHelpNew UNDO
            = new BranchCommandHelpNew.Builder("/sg", "undo")
            .description("Undo block changing command(s)")
            .parameter("[number]", "a number to undo generation(s)")
            .build();

    public static final BranchCommandHelpNew REDO
            = new BranchCommandHelpNew.Builder("/sg", "redo")
            .description("Redo block changing command(s)")
            .parameter("[number]", "a number to redo generation(s)")
            .build();

}
