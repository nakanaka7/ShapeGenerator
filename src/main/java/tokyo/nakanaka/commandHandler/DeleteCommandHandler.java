package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.Player;

public class DeleteCommandHandler implements CommandHandler{

	@Override
	public String getDescription() {
		return "Delete the generated blocks";
	}

	@Override
	public String getLabel() {
		return "delete";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("del", "delete");
	}

	@Override
	public String getUsage() {
		return "delete/del";
	}

	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		return new ArrayList<>();
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
