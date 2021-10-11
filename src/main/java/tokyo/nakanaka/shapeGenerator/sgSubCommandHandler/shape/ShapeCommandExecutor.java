package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.shape;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgSubcommandHelps;

import java.util.List;

public class ShapeCommandExecutor implements SgSubcommandExecutor {
    private SelectionShapeStrategyRepository shapeStrtgRepo;

    public ShapeCommandExecutor(SelectionShapeStrategyRepository shapeStrtgRepo) {
        this.shapeStrtgRepo = shapeStrtgRepo;
    }

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
        String usage = Main.SG + " " + SgSublabel.SHAPE + " " + String.join(" ", SgSubcommandHelps.SHAPE.parameterSyntaxes());
        if(args.length != 1) {
            player.print(cmdLogColor.error() + "Usage: " + usage);
            return;
        }
        SelectionShape selShape;
        try{
            selShape = SelectionShape.valueOf(args[0].toUpperCase());
        }catch(IllegalArgumentException e) {
            player.print(cmdLogColor.error() + "Invalid shape");
            return;
        }
        if(!List.of(this.shapeStrtgRepo.registeredShapes()).contains(selShape)) {
            player.print(cmdLogColor.error() + "Unsupported shape");
            return;
        }
        SelectionShape original = playerData.getSelectionShape();
        if(selShape != original) {
            playerData.setSelectionShape(selShape);
            World world = player.getEntityPosition().world();
            SelectionData selData = this.shapeStrtgRepo.get(selShape).newSelectionData(world);
            playerData.setSelectionData(selData);
        }
        player.print(cmdLogColor.main() + "Set the shape -> " + selShape);
    }

}
