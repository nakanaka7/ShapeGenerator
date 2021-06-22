package tokyo.nakanaka.selection;

import static tokyo.nakanaka.logger.LogConstant.HEAD_NORMAL;
import static tokyo.nakanaka.logger.LogConstant.INDENT_NORMAL;

import java.util.List;

import tokyo.nakanaka.player.Player;

public class SelectionMessenger {
	private SelectionManager selManager;

	public SelectionMessenger(SelectionManager selManager) {
		this.selManager = selManager;
	}
	
	public void sendMessage(Player player) {
		SelectionBuilder builder = player.getSelectionBuilder();
		SelectionShape shape = this.selManager.getSelectionShape(builder);
		player.getLogger().print(HEAD_NORMAL + shape.toString() + " selection");
		List<String> lines = builder.getStateLines();
		for(String line : lines) {
			player.getLogger().print(INDENT_NORMAL + line);
		}
	}
}
