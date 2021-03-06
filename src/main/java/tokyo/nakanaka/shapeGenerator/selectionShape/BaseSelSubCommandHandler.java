package tokyo.nakanaka.shapeGenerator.selectionShape;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.List;

/**
 * A base abstract class to handle "/sg sel" subcommand. E is object type for parsing.
 */
public abstract class BaseSelSubCommandHandler<E> implements SubCommandHandler {
	private String subLabel;
	private String[] subArgsUsage;
	private SelectionDataFactory selDataFactory;

	/**
	 * @param subLabel sub label of the subcommand
	 * @param subArgsUsage usage of the subcommand arguments
	 */
	public BaseSelSubCommandHandler(String subLabel, String[] subArgsUsage, SelectionDataFactory selDataFactory) {
		this.subLabel = subLabel;
		this.subArgsUsage = subArgsUsage;
		this.selDataFactory = selDataFactory;
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] subArgs, CommandLogColor cmdLogColor) {
		E value;
		try {
			value = this.parse(player, subArgs);
		}catch(IllegalArgumentException e) {
			player.print(cmdLogColor.error() + "Usage: /sg sel " + this.subLabel + " " + String.join(" ", this.subArgsUsage));
			return;
		}
		World evtWorld = player.getBlockPosition().world();
		SelectionData selData = playerData.getSelectionData();
		if(!evtWorld.equals(selData.world())) {
			playerData.setSelectionData(this.selDataFactory.newSelectionData(evtWorld));
		}
		playerData.getSelectionData().setExtraData(this.subLabel, value);
		List<String> lines = MessageUtils.selectionMessage(cmdLogColor.main(), playerData.getSelectionShape(), playerData.getSelectionData());
		lines.stream().forEach(player::print);
	}
	
	/**
	 * @param subArgs the arguments of the subcommand
	 * @return an object which was parsed.
	 * @throws IllegalArgumentException if this cannot parse the arguments to an object
	 */
	protected abstract E parse(Player player, String[] subArgs);
	
}
