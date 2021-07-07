package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.player.Player;

public interface SgSubRootCommandHandler extends SgSubCommandHandler{
	List<Pair<String, String>> getSubCommandDescriptions(Player player);	
}
