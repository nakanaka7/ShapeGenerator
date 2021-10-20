package tokyo.nakanaka.shapeGenerator.sgSubcommand.sel;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.HashMap;
import java.util.Map;

public class SelCommandExecutor implements SgSubcommandExecutor {
    private CommandLogColor cmdLogColor;
    private Map<String, SubCommandHandler> commonMap = new HashMap<>();
    private Map<SelectionShape, Map<String, SubCommandHandler>> properMapMap = new HashMap<>();

    public SelCommandExecutor(CommandLogColor cmdLogColor,
            Map<SelectionShape, InitialSelectionDataCreator> dataCreatorMap,
            Map<SelectionShape, Map<String, SubCommandHandler>> properMapMap){
        this.cmdLogColor = cmdLogColor;
        this.commonMap.put("offset", new OffsetCommandHandler(dataCreatorMap));
        this.commonMap.put("reset", new ResetCommandHandler(dataCreatorMap));
        this.properMapMap = properMapMap;
    }

    public void onCommand(PlayerData playerData, Player player, String[] args) {
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
