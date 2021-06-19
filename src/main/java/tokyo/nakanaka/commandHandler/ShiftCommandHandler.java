package tokyo.nakanaka.commandHandler;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.command.UndoableCommand;

public class ShiftCommandHandler implements CommandHandler{

	@Override
	public String getDescription() {
		return "Shift the generated blocks";
	}

	@Override
	public String getLabel() {
		return "shift";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("shift");
	}

	@Override
	public String getUsage() {
		return "shift <dx> <dy> <dz>";
	}

	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		Pair<String, String> desDx = new Pair<>("<dx>", "displacement for x axis");
		Pair<String, String> desDy = new Pair<>("<dy>", "displacement for y axis");
		Pair<String, String> desDz = new Pair<>("<dz>", "displacement for z axis");
		return Arrays.asList(desDx, desDy, desDz);
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		UndoCommandManager undoManager = player.getUndoCommandManager();
		UndoableCommand cmd = undoManager.peekUndoCommand();
		return true;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length <= 3) {
			return Arrays.asList("-10", "-5", "0", "5", "10");
		}else {
			return Arrays.asList();
		}
	}

}
