package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.Item;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.player.HumanPlayer;
import tokyo.nakanaka.player.Player;

public class WandCommandHandler implements CommandHandler {
	private BranchCommandHelp cmdHelp;
	
	public WandCommandHandler() {
		this.cmdHelp = new BranchCommandHelp.Builder("wand")
				.description("Give player a wand")
				.build();
	}

	@Override
	public String getLabel() {
		return "wand";
	}

	@Override
	public CommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}

	@Override
	public void onCommand(Player player, String[] args) {
		if(!(player instanceof HumanPlayer)) {
			player.getLogger().print(LogColor.RED + "Only player can execute this command");
		}
		((HumanPlayer) player).giveItem(Item.BLAZE_ROD, 1);
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		return new ArrayList<>();
	}

}
