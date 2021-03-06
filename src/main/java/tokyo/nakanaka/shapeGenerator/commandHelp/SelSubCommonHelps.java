package tokyo.nakanaka.shapeGenerator.commandHelp;

public class SelSubCommonHelps {
    private SelSubCommonHelps(){
    }

    public static final BranchCommandHelp OFFSET
            = new BranchCommandHelp.Builder("offset")
            .description("Set offset")
            .parameter("[x]", "x-coordinate")
            .parameter("[y]", "y-coordinate")
            .parameter("[z]", "z-coordinate")
            .build();

    public static final BranchCommandHelp RESET
            = new BranchCommandHelp.Builder("reset")
            .description("Reset selection")
            .build();

}
