package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.MessageUtils;
import tokyo.nakanaka.shapeGenerator.SelectionBuilder;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

/**
 * A base abstract class to handle "/sg sel" subcommand. E is object type for parsing.
 */
public abstract class BaseSelSubCommandHandler<E> implements SubCommandHandler {
	private String usage;
	
	/**
	 * @param usage a usage of the subcommand
	 */
	public BaseSelSubCommandHandler(String usage) {
		this.usage = usage;
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] subArgs) {
		E value;
		try {
			value = this.parse(player, subArgs);
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Usage: " + this.usage);
			return;
		}
		World evtWorld = player.getBlockPosition().world();
		if(!evtWorld.equals(playerData.getSelectionBuilder().world())) {
			RegionData newRegData = this.newRegionData();
			SelectionBuilder newSelBuilder = new SelectionBuilder(evtWorld, newRegData);
			playerData.setSelectionBuilder(newSelBuilder);
		}
		this.setParsedValue(playerData.getSelectionBuilder().getRegionData(), value);
		List<String> lines = MessageUtils.selectionMessage(playerData.getSelectionShape(), playerData.getSelectionBuilder());
		lines.stream().forEach(player::print);
	}
	
	/**
	 * @param args the arguments of the subcommand
	 * @return an object which was parsed. This object will be set into the region data
	 * @throws IllegalArgumentException if this cannot parse the arguments to an object
	 */
	protected abstract E parse(Player player, String[] args);
	
	/**
	 * Returns new region data 
	 * @return new region data
	 */
	protected abstract RegionData newRegionData();

	/**
	 * Set the parsed object into the region data
	 * @param regData the region data
	 * @param parsed the parsed object which is given by parse() method
	 */
	protected abstract void setParsedValue(RegionData regData, E parsed);
	
}
