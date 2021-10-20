package tokyo.nakanaka.shapeGenerator.sgSubcommand.shift;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class ShiftConstants {
    private ShiftConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Shift the generated blocks")
            .parameter("<direction>", "the direction to shift")
            .parameter("<distance>", "distance of shift")
            .build();

}
