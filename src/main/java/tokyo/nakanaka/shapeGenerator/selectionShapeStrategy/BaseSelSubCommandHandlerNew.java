package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.MessageUtils;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

/**
 * A base abstract class to handle "/sg sel" subcommand. E is object type for parsing.
 */
public abstract class BaseSelSubCommandHandlerNew<E> implements SubCommandHandler {	
	private String subLabel;
	private String[] subArgsUsage;

	/**
	 * @param subLabel sub label of the subcommand
	 * @param subArgsUsage usage of the subcommand arguments
	 */
	public BaseSelSubCommandHandlerNew(String subLabel, String[] subArgsUsage) {
		this.subLabel = subLabel;
		this.subArgsUsage = subArgsUsage;
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] subArgs) {
		E value;
		try {
			value = this.parse(player, subArgs);
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Usage: " + String.join(" ", this.subLabel, String.join(" ", this.subArgsUsage)));
			return;
		}
		World evtWorld = player.getBlockPosition().world();
		SelectionData selData = playerData.getSelectionData();
		if(!evtWorld.equals(selData.world())) {
			playerData.setSelectionData(new SelectionData(evtWorld, selData.defualtOffsetLabel(), selData.extraDataLabels()));
		}
		playerData.getSelectionData().setExtraData(this.subLabel, value);
		List<String> lines = MessageUtils.selectionMessage(playerData.getSelectionShape(), playerData.getSelectionData());
		lines.stream().forEach(player::print);
	}
	
	/**
	 * @param subArgs the arguments of the subcommand
	 * @return an object which was parsed.
	 * @throws IllegalArgumentException if this cannot parse the arguments to an object
	 */
	protected abstract E parse(Player player, String[] subArgs);
	
}
