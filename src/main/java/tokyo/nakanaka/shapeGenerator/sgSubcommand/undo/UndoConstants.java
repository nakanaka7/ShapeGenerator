package tokyo.nakanaka.shapeGenerator.sgSubcommand.undo;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class UndoConstants {
    private UndoConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Undo block changing command(s)")
            .parameter("[number]", "a number to undo generation(s)")
            .build();

}
