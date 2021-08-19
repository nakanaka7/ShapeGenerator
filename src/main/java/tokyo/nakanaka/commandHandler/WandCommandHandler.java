package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.player.HumanPlayer;
import tokyo.nakanaka.player.User;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.Item;

public class WandCommandHandler implements CommandHandler {
	private SelectionStrategySource selStraSource;
	
	public WandCommandHandler(SelectionStrategySource selStraSource) {
		this.selStraSource = selStraSource;
	}

	@Override
	public String getLabel() {
		return "wand";
	}

	@Override
	public String getDescription() {
		return "Give player a wand";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		return new ArrayList<>();
	}
	
	@Override
	public boolean onCommand(User user, String[] args) {
		if(!(user instanceof HumanPlayer)) {
			user.getLogger().print(LogDesignColor.ERROR + "Only player can execute this command");
		}
		HumanPlayer hp = (HumanPlayer) user;
		hp.giveItem(Item.BLAZE_ROD, 1);
		SelectionShape shape = user.getSelectionShape();
		SelectionStrategy strategy = this.selStraSource.get(shape);
		Logger logger = user.getLogger();
		logger.print(LogDesignColor.NORMAL + "Gave wand to " + hp.getName());
		new SelectionMessenger().printClickDescription(logger, strategy);
		return true;
	}

	@Override
	public List<String> onTabComplete(User user, String[] args) {
		return new ArrayList<>();
	}

}
