package tokyo.nakanaka.shapeGenerator.sgSubcommand.sel;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.List;
import java.util.Map;

public class ResetCommandHandler implements SubCommandHandler {
	private Map<SelectionShape, InitialSelectionDataCreator> dataCreatorMap;

	public ResetCommandHandler(Map<SelectionShape, InitialSelectionDataCreator> dataCreatorMap) {
		this.dataCreatorMap = dataCreatorMap;
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
		if(args.length != 0) {
			player.print(cmdLogColor.error() + "Usage: " + "/sg sel reset");
			return;
		}
		World world = player.getEntityPosition().world();
		SelectionShape shape = playerData.getSelectionShape();
		SelectionData newSelData = this.dataCreatorMap.get(shape)
				.newSelectionData(world);
		playerData.setSelectionData(newSelData);
		List<String> lines = MessageUtils.selectionMessage(cmdLogColor.main(), shape, newSelData);
		for(String line : lines) {
			player.print(line);
		}
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return List.of();
	}

}
