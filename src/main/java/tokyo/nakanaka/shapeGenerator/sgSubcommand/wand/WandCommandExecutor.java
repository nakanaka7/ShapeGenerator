package tokyo.nakanaka.shapeGenerator.sgSubcommand.wand;

import tokyo.nakanaka.*;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.Main;
import tokyo.nakanaka.shapeGenerator.SgSubcommandExecutor;
import tokyo.nakanaka.shapeGenerator.SgSublabel;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public class WandCommandExecutor implements SgSubcommandExecutor {
    private CommandLogColor cmdLogColor;

    public WandCommandExecutor(CommandLogColor cmdLogColor) {
        this.cmdLogColor = cmdLogColor;
    }

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args) {
        String usage = Main.SG + " " + SgSublabel.WAND + " " + String.join(" ", WandConstants.HELP.parameterSyntaxes());
        if(args.length != 0) {
            player.print(cmdLogColor.error() + "Usage: " + usage);
            return;
        }
        Item item = new Item(new NamespacedID("minecraft", "blaze_rod"));
        Enchantment ench = new Enchantment(new NamespacedID("minecraft", "power"), 1);
        ItemStackData itemStackData = new ItemStackData(item, 1, ench);
        player.giveItem(itemStackData);
    }

}
