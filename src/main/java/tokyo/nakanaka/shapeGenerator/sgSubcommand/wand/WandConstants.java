package tokyo.nakanaka.shapeGenerator.sgSubcommand.wand;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class WandConstants {
    private WandConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Give a player a wand")
            .build();

}
