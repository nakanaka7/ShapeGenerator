package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Player;

public class UndoCommandHandler implements CommandHandler{

	@Override
	public String getLabel() {
		return "undo";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("undo");
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		return new ArrayList<>();
	}

}
