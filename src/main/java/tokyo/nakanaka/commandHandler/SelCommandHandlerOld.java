package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.selection.SelectionManager;
import tokyo.nakanaka.selection.SelectionMessengerOld;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.world.World;

public class SelCommandHandlerOld implements SubCommandHandler{
	private SelectionManager selManager;
	private static final String RESET = "reset";
	private CommandHelp help = new CommandHelp.Builder("sel")
			.description("Specify a selection/ See each shape help")
			.build();
	
	public SelCommandHandlerOld(SelectionManager selManager) {
		this.selManager = selManager;
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
			new SelectionMessengerOld(this.selManager).sendMessage(player);
			return true;
		}
		if(!world.equals(builder.getWorld())) {
			SelectionShape shape = this.selManager.getShape(builder);
			SelectionBuilder newBuilder = this.selManager.newInstance(shape, world);
			player.setSelectionBuilder(newBuilder);
		}
		builder.onCommand(player.getLogger(), playerPos, label, shiftArgs);
		new SelectionMessengerOld(this.selManager).sendMessage(player);
		return true;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		SelectionBuilder builder = player.getSelectionBuilder();
		if(args.length == 1) {
			List<String> list = new ArrayList<>(builder.getLabelList());
			list.add(RESET);
			return list;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		return builder.onTabComplete(label, shiftArgs);
	}
}
