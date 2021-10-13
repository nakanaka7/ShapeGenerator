package tokyo.nakanaka.shapeGenerator.sgSubcommand.genr;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class GenrConstants {
    private GenrConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Generate block(s) in the selection")
            .parameter("<block>", "the block to generate")
            .build();

}
