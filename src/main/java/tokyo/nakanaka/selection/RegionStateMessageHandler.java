package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.Pair;

public interface RegionStateMessageHandler {
	public List<Pair<String, String>> onMessage(RegionBuilder builder);
}
