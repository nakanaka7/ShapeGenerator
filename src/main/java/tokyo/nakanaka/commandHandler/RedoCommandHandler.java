package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Player;

public class RedoCommandHandler implements CommandHandler{

	@Override
	public String getLabel() {
		return "redo";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("redo");
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
