package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.Item;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.HumanPlayer;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;

public class WandCommandHandler implements CommandHandler {
	private BranchCommandHelp cmdHelp;
	private SelectionStrategySource selStraSource;
	
	public WandCommandHandler(SelectionStrategySource selStraSource) {
		this.cmdHelp = new BranchCommandHelp.Builder("wand")
				.description("Give player a wand")
				.build();
		this.selStraSource = selStraSource;
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
		HumanPlayer hp = (HumanPlayer) player;
		hp.giveItem(Item.BLAZE_ROD, 1);
		SelectionShape shape = player.getSelectionShape();
		SelectionStrategy strategy = this.selStraSource.get(shape);
		Logger logger = player.getLogger();
		logger.print(LogColor.DARK_AQUA + "Gave wand to " + hp.getName());
		new SelectionMessenger().printClickDescription(logger, strategy);
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		return new ArrayList<>();
	}

}
