package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.selection.SelectionManager;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.world.World;

public class SelCommandHandler implements CommandHandler{
	private SelectionManager selManager;
	
	public SelCommandHandler(SelectionManager selManager) {
		this.selManager = selManager;
	}

	@Override
	public String getDescription() {
		return "Specify a selection";
	}
	
	@Override
	public String getLabel() {
		return "selection";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("sel");
	}

	@Override
	public String getUsage() {
		return "See each shape usage";
	}
	
	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		return new ArrayList<>();
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		World world = player.getWorld();
		int offsetX = player.getX();
		int offsetY = player.getY();
		int offsetZ = player.getZ();
		SelectionBuilder builder = player.getSelectionBuilder();
		if(args.length == 1 && args[0].equals("reset")){
			SelectionShape shape = this.selManager.getSelectionShape(builder);
			SelectionBuilder newBuilder = this.selManager.getNewSelectionBuilderInstance(shape);
			player.setSelectionBuilder(newBuilder);
			new SelectionMessenger(this.selManager).sendMessage(player);
			return true;
		}
		if(!world.equals(builder.getWorld())) {
			SelectionShape shape = this.selManager.getSelectionShape(builder);
			SelectionBuilder newBuilder = this.selManager.getNewSelectionBuilderInstance(shape);
			player.setSelectionBuilder(newBuilder);
		}
		builder.onCommand(offsetX, offsetY, offsetZ, args);
		new SelectionMessenger(this.selManager).sendMessage(player);
		return true;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		SelectionBuilder builder = player.getSelectionBuilder();
		List<String> list = new ArrayList<>(builder.onTabComplete(args));
		if(args.length == 1) {
			list.add("reset");
		}
		return list;
	}
}
