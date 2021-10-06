package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerDataRepository;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.genr.GenrExecutor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgBranchHelpConstants;

import java.util.HashMap;
import java.util.Map;

public class SgCommandExecutor {
    private PlayerDataRepository playerDataRepository;
    private Map<String, SgSubCommandExecutor> cmdExecutorMap = new HashMap<>();
    private CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);

    public SgCommandExecutor(CommandLogColor cmdLogColor, SelectionShapeStrategyRepository shapeStrtgRepo) {
        this.cmdExecutorMap.put("genr", new GenrExecutor(cmdLogColor, shapeStrtgRepo));
    }

    /**
     * Handles "/sg" command
     * @param cmdSender a command sender
     * @param args arguments of the command line
     */
    public void onCommand(CommandSender cmdSender, String[] args) {
        if(!(cmdSender instanceof Player player)) {
            cmdSender.print(cmdLogColor.error() + "Player only command");
            return;
        }
        if(args.length == 0) {
            cmdSender.print(cmdLogColor.error() + "Usage: /sg <subcommand>");
            cmdSender.print(cmdLogColor.error() + "Run \"" + SgBranchHelpConstants.HELP.syntax() + "\" for help");
            return;
        }
        String subLabel = args[0];
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, args.length - 1);
        SgSubCommandExecutor sgSubCmdExecutor = this.cmdExecutorMap.get(subLabel);
        if(sgSubCmdExecutor == null) {
            cmdSender.print(cmdLogColor.error() + "Unknown subcommand");
            cmdSender.print(cmdLogColor.error() + "Run \"" + SgBranchHelpConstants.HELP.syntax() + "\" for help");
            return;
        }
        PlayerData playerData = this.playerDataRepository.preparePlayerData(player);
        sgSubCmdExecutor.onCommand(playerData, player, subArgs);
    }

}
