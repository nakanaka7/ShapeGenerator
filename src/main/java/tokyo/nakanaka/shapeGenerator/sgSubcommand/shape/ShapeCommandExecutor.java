package tokyo.nakanaka.shapeGenerator.sgSubcommand.shape;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubcommandHelp.SgSubcommandHelps;

import java.util.Map;

public class ShapeCommandExecutor implements SgSubcommandExecutor {
    private Map<SelectionShape, InitialSelectionDataCreator> dataCreatorMap;

    public ShapeCommandExecutor(Map<SelectionShape, InitialSelectionDataCreator> dataCreatorMap){
        this.dataCreatorMap = dataCreatorMap;
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
        if(!this.dataCreatorMap.containsKey(selShape)) {
            player.print(cmdLogColor.error() + "Unsupported shape");
            return;
        }
        SelectionShape original = playerData.getSelectionShape();
        if(selShape != original) {
            playerData.setSelectionShape(selShape);
            World world = player.getEntityPosition().world();
            SelectionData selData = this.dataCreatorMap.get(selShape).newSelectionData(world);
            playerData.setSelectionData(selData);
        }
        player.print(cmdLogColor.main() + "Set the shape -> " + selShape);
    }

}
