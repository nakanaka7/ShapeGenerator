package tokyo.nakanaka.shapeGenerator.sgSubcommand.redo;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class RedoConstants {
    private RedoConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Redo block changing command(s)")
            .parameter("[number]", "a number to redo generation(s)")
            .build();

}
