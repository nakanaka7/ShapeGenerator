package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.SelSubCommandHandler;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.selection.SelectionManager;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.SelectionStrategy;
import tokyo.nakanaka.world.World;

public class SelCommandHandlerNew implements SubCommandHandler{
	private Map<SelectionShape, SelectionStrategy> strategyMap = new HashMap<>();
	private static final String RESET = "reset";
	private CommandHelp help = new CommandHelp.Builder("sel")
			.description("Specify a selection/ See each shape help")
			.build();
	@Deprecated
	private SelectionManager selManager;
	
	public SelCommandHandlerNew(Map<SelectionShape, SelectionStrategy> strategyMap) {
		this.strategyMap = strategyMap;
	}

	@Override
	public CommandHelp getCommandHelp() {
		return this.help;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 0) {
			return false;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		World world = player.getWorld();
		BlockVector3D playerPos = new BlockVector3D(player.getX(), player.getY(), player.getZ());
		SelectionBuilder builder = player.getSelectionBuilder();
		if(label.equals(RESET)){
			SelectionShape shape = this.selManager.getShape(builder);
			SelectionBuilder newBuilder = this.selManager.newInstance(shape, world);
			player.setSelectionBuilder(newBuilder);
			new SelectionMessenger(this.selManager).sendMessage(player);
			return true;
		}
		if(!world.equals(builder.getWorld())) {
			SelectionShape shape = this.selManager.getShape(builder);
			SelectionBuilder newBuilder = this.selManager.newInstance(shape, world);
			player.setSelectionBuilder(newBuilder);
		}
		builder.onCommand(player.getLogger(), playerPos, label, shiftArgs);
		new SelectionMessenger(this.selManager).sendMessage(player);
		return true;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		SelectionShape shape = player.getSelectionBuildingData().getSelectionShape();
		List<SelSubCommandHandler> cmdHandlerList = this.strategyMap.get(shape).getSelSubCommandHandlers();
		if(args.length == 1) {
			List<String> list = new ArrayList<>();
			for(SelSubCommandHandler handler : cmdHandlerList) {
				list.add(handler.getLabel());
			}
			list.add(RESET);
			return list;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		for(SelSubCommandHandler handler : cmdHandlerList) {
			if(handler.getLabel().equals(label)) {
				return handler.onTabComplete(shiftArgs);
			}
		}
		return new ArrayList<>();
	}
}
