package tokyo.nakanaka.shapeGenerator.sgSubcommand.sel;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

public class SelCommandExecutor implements SgSubcommandExecutor {

    private Map<String, SubCommandHandler> commonMap = new HashMap<>();
    private Map<SelectionShape, Map<String, SubCommandHandler>> properMapMap = new HashMap<>();

    public SelCommandExecutor(SelectionShapeStrategyRepository shapeStrtgRepo) {
        this.commonMap.put("offset", new OffsetCommandHandler(shapeStrtgRepo));
        this.commonMap.put("reset", new ResetCommandHandler(shapeStrtgRepo));
        for(SelectionShape selShape : shapeStrtgRepo.registeredShapes()) {
            SelectionShapeStrategy selStrtg = shapeStrtgRepo.get(selShape);
            this.properMapMap.put(selShape, selStrtg.selSubCommandHandlerMap());
        }
    }

    public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
        String usage = Main.SG + " " + SgSublabel.SEL + " " + String.join(" ", SelConstants.HELP.parameterSyntaxes());
        SelectionShape shape = playerData.getSelectionShape();
        if(args.length == 0) {
            player.print(cmdLogColor.error() + "Usage:" + usage);
            player.print(cmdLogColor.error() + "See help");
            return;
        }
        String subLabel = args[0];
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, args.length - 1);
        SubCommandHandler commonHandler = this.commonMap.get(subLabel);
        if(commonHandler != null) {
            commonHandler.onCommand(playerData, player, subArgs, cmdLogColor);
            return;
        }
        Map<String, SubCommandHandler> properMap = this.properMapMap.get(shape);
        SubCommandHandler properHandler = properMap.get(subLabel);
        if(properHandler != null) {
            properHandler.onCommand(playerData, player, subArgs, cmdLogColor);
            return;
        }
        player.print(cmdLogColor.error() + "Unkown subcommand");
        player.print(cmdLogColor.error() + "See help");
    }

}
