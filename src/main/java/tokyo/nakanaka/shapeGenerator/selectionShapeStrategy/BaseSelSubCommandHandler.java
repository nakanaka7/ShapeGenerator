package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.MessageUtils;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.user.UserData;

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
	public void onCommand(UserData userData, Player player, String[] subArgs) {
		E value;
		try {
			value = this.parse(player, subArgs);
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Usage: " + this.usage);
			return;
		}
		World evtWorld = player.getBlockPosition().world();
		if(!evtWorld.equals(userData.getSelectionData().getWorld())) {
			RegionData newRegData = this.newRegionData();
			SelectionData newSelData = new SelectionData(evtWorld, newRegData);
			userData.setSelectionData(newSelData);
		}
		this.setParsedValue(userData.getSelectionData().getRegionData(), value);
		List<String> lines = MessageUtils.selectionMessage(userData.getSelectionShape(), userData.getSelectionData());
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
