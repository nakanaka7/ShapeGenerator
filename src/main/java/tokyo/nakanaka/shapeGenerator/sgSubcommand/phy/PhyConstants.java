package tokyo.nakanaka.shapeGenerator.sgSubcommand.phy;

import tokyo.nakanaka.shapeGenerator.commandHelp.BranchCommandHelp;

public class PhyConstants {
    private PhyConstants(){
    }

    public static final BranchCommandHelp HELP
            = new BranchCommandHelp.Builder()
            .description("Toggle physics option for generating blocks")
            .parameter("true|false", "an option for physics")
            .build();

}
